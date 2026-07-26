package com.github.lohithpuvvala.projectmanagement.backend.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateBoardRequest(
        @NotBlank
        @Size(max = 100)
        String name
) { }
