package com.github.lohithpuvvala.projectmanagement.backend.auth.service;

import com.github.lohithpuvvala.projectmanagement.backend.security.jwt.JwtService;
import com.github.lohithpuvvala.projectmanagement.backend.user.dto.LoginRequest;
import com.github.lohithpuvvala.projectmanagement.backend.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String accessToken = jwtService.generateToken(request.getEmail());

        return new LoginResponse(accessToken, "Bearer");
    }
}
