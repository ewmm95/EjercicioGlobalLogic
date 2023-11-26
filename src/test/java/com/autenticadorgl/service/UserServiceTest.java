package com.autenticadorgl.service;

import com.autenticadorgl.dto.*;
import com.autenticadorgl.entity.PhoneEntity;
import com.autenticadorgl.entity.UserEntity;
import com.autenticadorgl.exception.UserException;
import com.autenticadorgl.repository.PhoneRepository;
import com.autenticadorgl.repository.UserRepository;
import com.autenticadorgl.service.impl.UserServiceImpl;
import com.autenticadorgl.util.JwtUtil;
import com.autenticadorgl.util.ParseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ParseUtil parseUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegisterUserSuccess() throws Exception {
        String password = "secret";
        String email = "asd@secret.cl";

        UserRegistrationRequestDTO newUser = new UserRegistrationRequestDTO();
        newUser.setPassword(password);
        newUser.setEmail(email);
        List<PhoneDTO> phones = new ArrayList<PhoneDTO>();
        phones.add(new PhoneDTO(1, 1, "asd"));
        phones.add(new PhoneDTO(1, 2, "asd"));
        phones.add(new PhoneDTO(1, 3, "asd"));
        newUser.setPhones(phones);

        UserEntity saved = new UserEntity();
        //PhoneEntity phoneEntity = new PhoneEntity();
        UserRegistrationResponseDTO response = new UserRegistrationResponseDTO();

        when(passwordEncoder.encode(anyString()))
                .thenReturn(password);
        when(jwtUtil.generateToken(anyString()))
                .thenReturn("token");
        //when(phoneRepository.save(any(PhoneEntity.class)))
        //        .thenReturn(phoneEntity);
        when(parseUtil.userRegistrationRequestToUserEntity(any(), any(), any()))
                .thenReturn(saved);
        when(parseUtil.userEntityToUserRegistrationResponseDTO(any()))
                .thenReturn(response);
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(saved);
        when(userRepository.findByEmail(newUser.getEmail()))
                .thenReturn(Optional.empty());
        UserRegistrationResponseDTO result = userService.registerUser(newUser);

        //verify(userRepository).save(saved);
        assertNotNull(result);
    }

    @Test
    public void testRegisterUserFailUserExists() {
        UserRegistrationRequestDTO existingUser = new UserRegistrationRequestDTO();
        UserEntity userMailVerification = new UserEntity();
        when(userRepository.findByEmail(existingUser.getEmail()))
                .thenReturn(Optional.of(userMailVerification));
        assertThrows(UserException.class, () -> {
            userService.registerUser(existingUser);
        });
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        String password = "a2asSffdfd2f";
        String encryptedPassword = "$2a$10$R/7dxTdZzmKcfWLgUv406uCN69WQRP0ZqRCCzFcfzyskUKCfM.T4O";
        String email = "asd@secret.cl";

        UserLoginRequestDTO newUser = new UserLoginRequestDTO();
        newUser.setPassword(password);
        newUser.setEmail(email);
        UserEntity saved = new UserEntity();
        saved.setPassword(encryptedPassword);
        UserDTO userResponseMock = new UserDTO();
        userResponseMock.setEmail(email);
        userResponseMock.setToken("token");

        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);
        when(userRepository.findByEmail(newUser.getEmail()))
                .thenReturn(Optional.of(saved));
        when(parseUtil.userEntityToUserDTO(any()))
                .thenReturn(userResponseMock);

        UserDTO response = userService.loginUser(newUser);


        assertEquals(response.getEmail(), newUser.getEmail());
        assertNotNull(response.getToken());
    }

    @Test
    public void testLoginUserErrorNotFoundEmail(){
        String password = "secret";
        String email = "asd@secret.cl";

        UserLoginRequestDTO newUser = new UserLoginRequestDTO();
        newUser.setPassword(password);
        newUser.setEmail(email);

        when(userRepository.findByEmail(newUser.getEmail()))
                .thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> {
            userService.loginUser(newUser);
        });
    }

    @Test
    public void testLoginUserErrorWrongPass(){
        String password = "secret";
        String email = "asd@secret.cl";

        UserLoginRequestDTO newUser = new UserLoginRequestDTO();
        newUser.setPassword(password);
        newUser.setEmail(email);
        UserEntity saved = new UserEntity();

        when(userRepository.findByEmail(newUser.getEmail()))
                .thenReturn(Optional.of(saved));

        assertThrows(UserException.class, () -> {
            userService.loginUser(newUser);
        });
    }

    @Test
    public void testGetPhones() throws Exception {
        String password = "secret";
        String email = "asd@secret.cl";

        UserDTO newUser = new UserDTO();
        newUser.setPassword(password);
        newUser.setEmail(email);

        List<PhoneDTO> phones = new ArrayList<PhoneDTO>();
        phones.add(new PhoneDTO(1, 1, "asd"));
        phones.add(new PhoneDTO(1, 2, "asd"));
        phones.add(new PhoneDTO(1, 3, "asd"));
        newUser.setPhones(phones);

        when(userRepository.findByEmail(newUser.getEmail()))
                .thenReturn(Optional.of(new UserEntity()));

        when(parseUtil.userEntityToUserDTO(any()))
                .thenReturn(newUser);

        List<PhoneDTO> response = userService.getPhones(email);
        assertEquals(response, phones);
    }

}