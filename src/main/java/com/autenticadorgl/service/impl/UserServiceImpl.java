package com.autenticadorgl.service.impl;

import com.autenticadorgl.constants.Constants;
import com.autenticadorgl.dto.*;
import com.autenticadorgl.entity.PhoneEntity;
import com.autenticadorgl.entity.UserEntity;
import com.autenticadorgl.exception.UserException;
import com.autenticadorgl.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.autenticadorgl.repository.PhoneRepository;
import com.autenticadorgl.repository.UserRepository;
import com.autenticadorgl.service.UserService;
import com.autenticadorgl.util.ParseUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final ParseUtil parseUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(
            UserRepository userRepository,
            PhoneRepository phoneRepository,
            ParseUtil parseUtil,
            BCryptPasswordEncoder passwordEncoder,
            JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.parseUtil = parseUtil;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO user) throws Exception {
        //valida duplicidad usuario
        Optional<UserEntity> userMailVerification = userRepository.findByEmail(user.getEmail());
        if(!userMailVerification.isEmpty()) throw new UserException(409, Constants.EMAIL_UNIQUE);
        //registro usuario
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        String token = jwtUtil.generateToken(user.getEmail());
        UserEntity insert = userRepository.save(parseUtil.userRegistrationRequestToUserEntity(user, encryptedPassword, token));
        //registro telefonos
        List<PhoneEntity> phones = parseUtil.listPhoneDTOToListPhoneEntity(user.getPhones(), insert);
        phones.stream().forEach(phoneEntity -> phoneRepository.save(phoneEntity));
        return parseUtil.userEntityToUserRegistrationResponseDTO(insert);
    }

    @Override
    public UserDTO loginUser(UserLoginRequestDTO user) throws Exception {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserException(409, Constants.EMAIL_NOTFOUND));
        if(!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) throw new UserException(401, Constants.PASSWORD_WRONG);
        //actualiza campos
        userEntity.setToken(jwtUtil.generateToken(user.getEmail()));
        userEntity.setLastLogin(LocalDateTime.now());
        UserEntity updatedUser = userRepository.save(userEntity);
        return parseUtil.userEntityToUserDTO(updatedUser);
    }

    @Override
    public List<PhoneDTO> getPhones(String email) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if(userEntity.isEmpty()) throw new UserException(409, Constants.EMAIL_NOTFOUND);
        return parseUtil.userEntityToUserDTO(userEntity.get()).getPhones();
    }

}
