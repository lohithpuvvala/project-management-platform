package com.github.lohithpuvvala.projectmanagement.backend.user.service;

import com.github.lohithpuvvala.projectmanagement.backend.user.dto.RegisterRequest;
import com.github.lohithpuvvala.projectmanagement.backend.user.dto.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest register);
}
