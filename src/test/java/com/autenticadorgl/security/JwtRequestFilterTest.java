package com.autenticadorgl.security;


import com.autenticadorgl.constants.Constants;
import com.autenticadorgl.service.impl.CustomUserDetailsService;
import com.autenticadorgl.util.JwtUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtRequestFilterTest {

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @Test
    public void testValidTokenAuthentication() throws Exception {
        String email = "asd@secret.cl";
        String token = "Bearer token";

        when(request.getHeader("token")).thenReturn(token);
        when(jwtUtil.extractEmail(any())).thenReturn(email);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtUtil.validateToken(any(), any())).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testInvalidTokenErrorResponse() throws Exception {
        when(request.getHeader("token")).thenReturn("Bearer token");
        doThrow(new JWTVerificationException("Token no valido")).when(jwtUtil).extractEmail(any());

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        writer.flush();

        assertTrue(stringWriter.toString().contains(Constants.NOT_AUTHORIZED));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
