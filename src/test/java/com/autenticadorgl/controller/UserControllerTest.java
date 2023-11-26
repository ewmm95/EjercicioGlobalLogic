package com.autenticadorgl.controller;

import com.autenticadorgl.dto.PhoneDTO;
import com.autenticadorgl.dto.UserRegistrationRequestDTO;
import com.autenticadorgl.dto.UserRegistrationResponseDTO;
import com.autenticadorgl.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void getPhonesTest() throws Exception {
        String email = "test@example.com";
        List<PhoneDTO> phones = new ArrayList<PhoneDTO>();
        phones.add(new PhoneDTO(1, 1, "asd"));
        phones.add(new PhoneDTO(1, 2, "asd"));
        phones.add(new PhoneDTO(1, 3, "asd"));

        when(userService.getPhones(email)).thenReturn(phones);

        ResponseEntity<List<PhoneDTO>> response = userController.getPhones(email);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(phones, response.getBody());
    }

    @Test
    public void registerUserTest() throws Exception {
        String email = "test@example.com";
        String password = "a2asSffdfd2f";
        UserRegistrationRequestDTO registrationRequest = new UserRegistrationRequestDTO(email, password);
        UserRegistrationResponseDTO expectedResponse = new UserRegistrationResponseDTO();

        when(userService.registerUser(any(UserRegistrationRequestDTO.class))).thenReturn(expectedResponse);

        ResponseEntity<UserRegistrationResponseDTO> response = userController.registerUser(registrationRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

}
