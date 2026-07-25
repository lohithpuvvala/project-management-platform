package com.github.lohithpuvvala.projectmanagement.backend.project.dto.response;

import java.util.UUID;

public record ProjectCreateResponse(
        UUID id,
        String name,
        String description
) {
}
