package com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response;

import java.util.UUID;

public record OrganizationSummaryResponse(
        UUID id,
        String name
) { }
