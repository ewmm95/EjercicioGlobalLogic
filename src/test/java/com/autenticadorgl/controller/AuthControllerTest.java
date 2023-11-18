package com.autenticadorgl.controller;

import com.autenticadorgl.dto.UserDTO;
import com.autenticadorgl.dto.UserLoginRequestDTO;
import com.autenticadorgl.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController controller;

    @Test
    void loginUserTest() throws Exception {
        /*
        UserLoginRequestDTO request = new UserLoginRequestDTO();
        UserDTO expectedResponse = new UserDTO();

        when(userService.loginUser(any()))
                .thenReturn(expectedResponse);

        ResponseEntity<UserDTO> response = controller.loginUser(request);

        assertNotNull(response);
        assertEquals(expectedResponse, response.getBody());
        */
    }
}
