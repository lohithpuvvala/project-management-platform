package com.github.lohithpuvvala.projectmanagement.backend.project.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectDetailResponse(
        UUID id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
