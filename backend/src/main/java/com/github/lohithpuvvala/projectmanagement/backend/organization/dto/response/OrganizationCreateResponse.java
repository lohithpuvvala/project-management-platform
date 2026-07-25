package com.github.lohithpuvvala.projectmanagement.backend.organization.dto;

import java.util.UUID;

public record OrganizationCreateResponse(
        UUID id,
        String name,
        String description
) { }
