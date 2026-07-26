package com.github.lohithpuvvala.projectmanagement.backend.board.dto.response;

import java.util.UUID;

public record BoardSummaryResponse(
        UUID id,
        String name
) {
}
