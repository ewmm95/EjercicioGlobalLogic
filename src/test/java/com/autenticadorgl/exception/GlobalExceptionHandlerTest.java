package com.autenticadorgl.exception;

import com.autenticadorgl.constants.Constants;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testHandleException() {
        String errorMessage = "Error general";
        Exception exception = new Exception(errorMessage);

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleException(exception);
        check(response, HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    @Test
    public void testHandleAccessDeniedException() {
        String errorMessage = "Acceso denegado";
        AccessDeniedException exception = new AccessDeniedException(errorMessage);

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleAccessDeniedException(exception);

        check(response, HttpStatus.FORBIDDEN, errorMessage);
    }

    @Test
    public void testHandleUsernameNotFoundException() {
        String errorMessage = "Usuario no encontrado";
        UsernameNotFoundException exception = new UsernameNotFoundException(errorMessage);

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleUsernameNotFoundException(exception);

        check(response, HttpStatus.FORBIDDEN, errorMessage);
    }

    @Test
    public void testHandleJWTDecodeException() {
        String errorMessage = "TEST";
        JWTDecodeException exception = new JWTDecodeException(errorMessage);

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleJWTDecodeException(exception);

        check(response, HttpStatus.BAD_REQUEST, Constants.JWT_ERRORDECODE);
    }

    @Test
    public void testHandleTokenExpiredException() {
        String errorMessage = "TEST";
        TokenExpiredException exception = new TokenExpiredException(errorMessage);

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleTokenExpiredException(exception);

        check(response, HttpStatus.BAD_REQUEST, Constants.JWT_ERRORDECODE);
    }

    @Test
    public void testHandleNoSuchElementException() {
        String errorMessage = "TEST";
        NoSuchElementException exception = new NoSuchElementException(errorMessage);

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleNoSuchElementException(exception);

        check(response, HttpStatus.BAD_REQUEST, Constants.REQUEST_PENDPARAM);
    }

    private void check(ResponseEntity<ErrorResponseDTO> response, HttpStatus expectedStatus, String expectedMessage){
        assertNotNull(response);
        assertEquals(expectedStatus, response.getStatusCode());
        assertTrue(response.getBody().getErrors().stream().anyMatch(e -> e.getDetail().equals(expectedMessage)));
    }

}
