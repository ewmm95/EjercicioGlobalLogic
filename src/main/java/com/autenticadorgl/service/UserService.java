package com.autenticadorgl.service;

import com.autenticadorgl.dto.*;

import java.util.List;

public interface UserService {

    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO user) throws Exception;
    public UserDTO loginUser(UserLoginRequestDTO user) throws Exception;
    public List<PhoneDTO> getPhones(String email) throws Exception;

}
