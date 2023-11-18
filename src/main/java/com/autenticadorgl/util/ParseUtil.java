package com.autenticadorgl.util;

import com.autenticadorgl.dto.PhoneDTO;
import com.autenticadorgl.dto.UserDTO;
import com.autenticadorgl.dto.UserRegistrationRequestDTO;
import com.autenticadorgl.dto.UserRegistrationResponseDTO;
import com.autenticadorgl.entity.PhoneEntity;
import com.autenticadorgl.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ParseUtil {

    public UserEntity userRegistrationRequestToUserEntity(
            UserRegistrationRequestDTO user,
            String encryptedPassword,
            String token
    ){
        return new UserEntity(
                user.getName(),
                user.getEmail(),
                encryptedPassword,
                token
        );
    }

    public List<PhoneEntity> listPhoneDTOToListPhoneEntity(List<PhoneDTO> phones, UserEntity user){
        List<PhoneEntity> response = new ArrayList<>();
        if(Objects.nonNull(phones) && phones.size() > 0){
            phones.stream().forEach(phone -> {
                response.add(
                    new PhoneEntity(
                        user,
                        phone.getNumber(),
                        phone.getCitycode(),
                        phone.getCountrycode()
                    )
                );
            });
        }
        return response;
    }

    public UserDTO userEntityToUserDTO(UserEntity user){
        return new UserDTO(
                user.getId(),
                user.getCreated(),
                user.getLastLogin(),
                user.getToken(),
                user.isActive(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                this.listPhoneEntityToListPhoneDto(user.getPhones())
        );
    }

    public UserRegistrationResponseDTO userEntityToUserRegistrationResponseDTO(UserEntity user){
        return new UserRegistrationResponseDTO(
                user.getId(),
                user.getCreated(),
                user.getLastLogin(),
                user.getToken(),
                user.isActive()
        );
    }

    public List<PhoneDTO> listPhoneEntityToListPhoneDto(List<PhoneEntity> phones){
        List<PhoneDTO> response = new ArrayList<>();
        if(Objects.nonNull(phones) && phones.size() > 0){
            phones.stream().forEach(phone -> {
                response.add(
                    new PhoneDTO(
                        phone.getNumber(),
                        phone.getCitycode(),
                        phone.getCountrycode()
                    )
                );
            });
        }
        return response;
    }
}
