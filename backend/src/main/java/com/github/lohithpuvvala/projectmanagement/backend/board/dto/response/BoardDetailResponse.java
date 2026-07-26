package com.github.lohithpuvvala.projectmanagement.backend.board.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record BoardDetailResponse(
        UUID id,
        String name,
        boolean systemBoard,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
