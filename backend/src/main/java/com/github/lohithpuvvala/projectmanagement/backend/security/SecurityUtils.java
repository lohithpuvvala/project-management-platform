package com.github.lohithpuvvala.projectmanagement.backend.security;

import com.github.lohithpuvvala.projectmanagement.backend.common.exception.ResourceNotFoundException;
import com.github.lohithpuvvala.projectmanagement.backend.user.entity.User;
import com.github.lohithpuvvala.projectmanagement.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {
    private final UserRepository userRepository;

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getCurrentUserEmail(){
        return getAuthentication().getName();
    }

    public User getCurrentUser(){
        String email = getCurrentUserEmail();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
