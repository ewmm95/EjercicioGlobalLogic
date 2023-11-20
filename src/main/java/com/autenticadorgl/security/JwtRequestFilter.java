package com.autenticadorgl.security;

import com.autenticadorgl.constants.Constants;
import com.autenticadorgl.exception.UserException;
import com.autenticadorgl.service.impl.CustomUserDetailsService;
import com.autenticadorgl.util.JwtUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("token");

        String username = null;
        String jwt = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                    username = jwtUtil.extractEmail(jwt);
            }else{
                throw new IllegalArgumentException("");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            chain.doFilter(request, response);
        } catch (JWTVerificationException ex){ // Error verificación token
            errorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    Constants.NOT_AUTHORIZED
            );
        } catch (IllegalArgumentException ex){ // token invalido
            errorResponse(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    Constants.JWT_BADREQUEST
            );
        } catch (Exception ex){
            errorResponse(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    ":o!!"
            );
        }
    }

    /**
     * Excepciones respuesta estandar
     * @param response
     * @param code
     * @param detail
     * @throws IOException
     */
    private void errorResponse(HttpServletResponse response, int code, String detail) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String json = "{ \"errors\": [ { " +
                "\"timestamp\": \"" + timestamp + "\"," +
                "\"code\": " + code + "," +
                "\"detail\": \"" + detail + "\"} " +
                "] }";
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(json);
        System.out.println(timestamp+" | "+detail);
    }


}
