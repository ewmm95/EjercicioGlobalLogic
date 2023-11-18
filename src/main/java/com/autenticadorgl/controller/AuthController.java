package com.autenticadorgl.controller;

import com.autenticadorgl.dto.UserDTO;
import com.autenticadorgl.dto.UserLoginRequestDTO;
import com.autenticadorgl.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> loginUser(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) throws Exception {
        return ResponseEntity.ok(userService.loginUser(userLoginRequestDTO));
    }
}
