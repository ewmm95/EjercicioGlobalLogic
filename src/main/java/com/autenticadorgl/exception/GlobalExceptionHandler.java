package com.autenticadorgl.exception;

import com.autenticadorgl.constants.Constants;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        System.out.println("handleException");

        System.out.println(ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
        errorResponse.addError(errorDetail);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        System.out.println("handleValidationException");

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    error.getDefaultMessage()
            );
            errorResponse.addError(errorDetail);
        });

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        System.out.println("handleAccessDeniedException");
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage()
        );
        errorResponse.addError(errorDetail);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        System.out.println("handleUsernameNotFoundException");

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage()
        );
        errorResponse.addError(errorDetail);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<Object> handleJWTDecodeException(JWTDecodeException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                Constants.JWT_ERRORDECODE
        );
        errorResponse.addError(errorDetail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpiredException(TokenExpiredException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                Constants.JWT_ERRORDECODE
        );
        errorResponse.addError(errorDetail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                Constants.REQUEST_PENDPARAM
        );
        errorResponse.addError(errorDetail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
