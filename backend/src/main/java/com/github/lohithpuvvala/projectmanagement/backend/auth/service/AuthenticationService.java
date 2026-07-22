package com.github.lohithpuvvala.projectmanagement.backend.auth.service;

import com.github.lohithpuvvala.projectmanagement.backend.user.dto.LoginRequest;
import com.github.lohithpuvvala.projectmanagement.backend.user.dto.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
}
