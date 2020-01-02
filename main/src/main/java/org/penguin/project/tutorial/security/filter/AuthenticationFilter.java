package org.penguin.project.tutorial.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.model.request.LoginRequest;
import org.penguin.project.tutorial.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter { //AbstractAuthenticationProcessingFilter

    private final UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String TOKEN_SECRET = "h4of9eh48vmg02nfu30v27yen295hfj65";

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.userService = userService;
        super.setAuthenticationManager(authenticationManager);
    }

    @PostConstruct
    public void afterConstructed() {
        System.out.println("Hello World, from Spring Boot 2!");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            String tokenPayload = request.getHeader("Authorization");
            log.info("Authorization from attemptAuthentication method: {}", tokenPayload);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        // Get User Details from Database
        String userName = ((User) auth.getPrincipal()).getUsername();
        Optional<org.penguin.project.tutorial.domain.User> optionalUser = userService.findByUsername(userName);

        // Generate JWT
        String token = Jwts.builder().setSubject(optionalUser.get().getId()).setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("3600000"))).signWith(SignatureAlgorithm.HS512, TOKEN_SECRET).compact();
        response.addHeader("Token", token);
        response.addHeader("UserID", optionalUser.get().getId());


//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authResult);
//        SecurityContextHolder.setContext(context);
//        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
//        failureHandler.onAuthenticationFailure(request, response, failed);
        super.unsuccessfulAuthentication(request, response, failed);
    }
}