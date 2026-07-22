package com.github.lohithpuvvala.projectmanagement.backend.user.service;

import com.github.lohithpuvvala.projectmanagement.backend.common.exception.EmailAlreadyExistsException;
import com.github.lohithpuvvala.projectmanagement.backend.user.dto.RegisterRequest;
import com.github.lohithpuvvala.projectmanagement.backend.user.dto.UserResponse;
import com.github.lohithpuvvala.projectmanagement.backend.user.entity.Role;
import com.github.lohithpuvvala.projectmanagement.backend.user.entity.User;
import com.github.lohithpuvvala.projectmanagement.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse register(RegisterRequest register) {
        if(userRepository.existsByEmail(register.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .email(register.getEmail())
                .password(register.getPassword()) // Temporary, We'll hash it later
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .role(savedUser.getRole())
                .build();
    }
}
