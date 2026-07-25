package com.github.lohithpuvvala.projectmanagement.backend.project.dto.response;

import java.util.UUID;

public record ProjectSummaryResponse(
        UUID id,
        String name
) {
}
