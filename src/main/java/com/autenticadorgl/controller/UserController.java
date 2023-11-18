package com.autenticadorgl.controller;

import com.autenticadorgl.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autenticadorgl.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/phones", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<PhoneDTO>> getPhones(@Valid String email) throws Exception {
        return ResponseEntity.ok(userService.getPhones(email));
    }

    @PostMapping(value = "/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserRegistrationResponseDTO> registerUser(@Valid @RequestBody UserRegistrationRequestDTO userRegistrationDTO) throws Exception {
        return ResponseEntity.ok(userService.registerUser(userRegistrationDTO));
    }
}
