package com.autenticadorgl.controller;

import com.autenticadorgl.dto.UserDTO;
import com.autenticadorgl.dto.UserLoginRequestDTO;
import com.autenticadorgl.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Test
    void loginUserTest() throws Exception {
        String email = "test@example.com";
        String password = "encryptedPassword";
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setEmail(email);
        userLoginRequestDTO.setPassword(password);

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setEmail(email);
        expectedUserDTO.setToken("bearer token");

        when(userService.loginUser(any(UserLoginRequestDTO.class))).thenReturn(expectedUserDTO);

        ResponseEntity<UserDTO> response = authController.loginUser(userLoginRequestDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedUserDTO, response.getBody());
    }
}
