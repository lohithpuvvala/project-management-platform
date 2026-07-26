package com.github.lohithpuvvala.projectmanagement.backend.board.dto.response;

import java.util.UUID;

public record BoardCreateResponse(
        UUID id,
        String name
) { }
