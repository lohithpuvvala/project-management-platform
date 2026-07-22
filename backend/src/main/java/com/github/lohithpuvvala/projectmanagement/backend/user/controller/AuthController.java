package com.github.lohithpuvvala.projectmanagement.backend.user.controller;

import com.github.lohithpuvvala.projectmanagement.backend.user.dto.RegisterRequest;
import com.github.lohithpuvvala.projectmanagement.backend.user.dto.UserResponse;
import com.github.lohithpuvvala.projectmanagement.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest register) {
        return userService.register(register);
    }
}
