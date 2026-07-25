package com.github.lohithpuvvala.projectmanagement.backend.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProjectRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 500)
        String description
) {
}
