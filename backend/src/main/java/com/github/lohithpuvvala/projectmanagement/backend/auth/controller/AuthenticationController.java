package com.github.lohithpuvvala.projectmanagement.backend.auth.controller;

import com.github.lohithpuvvala.projectmanagement.backend.auth.service.AuthenticationService;
import com.github.lohithpuvvala.projectmanagement.backend.user.dto.LoginRequest;
import com.github.lohithpuvvala.projectmanagement.backend.user.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
