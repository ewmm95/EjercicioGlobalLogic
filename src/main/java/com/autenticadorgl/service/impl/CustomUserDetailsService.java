package com.autenticadorgl.service.impl;

import com.autenticadorgl.constants.Constants;
import com.autenticadorgl.entity.UserEntity;
import com.autenticadorgl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.EMAIL_NOTFOUND));

        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), getAuthorities(userEntity));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserEntity userEntity) {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }
}

