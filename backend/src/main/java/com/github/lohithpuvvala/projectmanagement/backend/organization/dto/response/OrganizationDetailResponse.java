package com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrganizationDetailResponse(
        UUID id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
