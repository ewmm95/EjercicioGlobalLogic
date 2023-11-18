package com.autenticadorgl.util;

import com.autenticadorgl.dto.PhoneDTO;
import com.autenticadorgl.dto.UserDTO;
import com.autenticadorgl.dto.UserRegistrationRequestDTO;
import com.autenticadorgl.entity.PhoneEntity;
import com.autenticadorgl.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParseUtilTest {

    private ParseUtil parseUtil;

    @BeforeEach
    public void setup() {
        parseUtil = new ParseUtil();
    }

    @Test
    public void testUserRegistrationRequestToUserEntity() {
        UserRegistrationRequestDTO request = new UserRegistrationRequestDTO();
        String encryptedPassword = "data:/plain;base64,SECRET";
        String token = "token123";

        UserEntity result = parseUtil.userRegistrationRequestToUserEntity(request, encryptedPassword, token);

        assertNotNull(result);
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getEmail(), result.getEmail());
        assertEquals(encryptedPassword, result.getPassword());
        assertEquals(token, result.getToken());
    }

    @Test
    public void testListPhoneDTOToListPhoneEntity() {
        List<PhoneDTO> phoneDTOs = new ArrayList<>();
        phoneDTOs.add(new PhoneDTO(1, 1, "asd"));
        phoneDTOs.add(new PhoneDTO(1, 2, "asd"));
        phoneDTOs.add(new PhoneDTO(1, 3, "asd"));

        UserEntity user = new UserEntity();
        List<PhoneEntity> result = parseUtil.listPhoneDTOToListPhoneEntity(phoneDTOs, user);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testUserEntityToUserDTO() {
        UserEntity userEntity = new UserEntity();

        List<PhoneEntity> phoneEntities = new ArrayList<>();
        userEntity.setPhones(phoneEntities);

        UserDTO result = parseUtil.userEntityToUserDTO(userEntity);

        assertNotNull(result);
        assertEquals(userEntity.getId(), result.getId());
        assertEquals(userEntity.getCreated(), result.getCreated());
        assertEquals(userEntity.getLastLogin(), result.getLastLogin());
        assertEquals(userEntity.getToken(), result.getToken());
        assertEquals(userEntity.isActive(), result.isActive());
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getEmail(), result.getEmail());
        assertEquals(userEntity.getPassword(), result.getPassword());
    }
}
