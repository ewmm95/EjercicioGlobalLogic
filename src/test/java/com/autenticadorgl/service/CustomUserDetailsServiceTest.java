package com.autenticadorgl.service;

import com.autenticadorgl.entity.UserEntity;
import com.autenticadorgl.repository.UserRepository;
import com.autenticadorgl.service.impl.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void loadUserByUsername_WithValidEmail_ReturnsUserDetails() {
        String email = "test@example.com";
        String password = "encryptedPassword";
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setEmail(email);
        mockUserEntity.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUserEntity));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
    }

    @Test
    public void loadUserByUsername_WithInvalidEmail_ThrowsUsernameNotFoundException() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(email);
        });
    }

}
