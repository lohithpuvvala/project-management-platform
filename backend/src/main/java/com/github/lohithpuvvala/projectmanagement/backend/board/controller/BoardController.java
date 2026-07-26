package com.github.lohithpuvvala.projectmanagement.backend.board.controller;

import com.github.lohithpuvvala.projectmanagement.backend.board.dto.request.CreateBoardRequest;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.request.UpdateBoardRequest;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.service.BoardService;
import com.github.lohithpuvvala.projectmanagement.backend.common.response.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizations/{organizationId}/projects/{projectId}/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardCreateResponse> createBoard(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @Valid @RequestBody CreateBoardRequest request
    ) {

        BoardCreateResponse response = boardService.createBoard(
                organizationId,
                projectId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<BoardSummaryResponse>> getBoards(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                boardService.getBoards(
                        organizationId,
                        projectId,
                        pageable
                )
        );
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponse> getBoardById(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID boardId
    ) {

        return ResponseEntity.ok(
                boardService.getBoardById(
                        organizationId,
                        projectId,
                        boardId
                )
        );
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponse> updateBoard(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID boardId,
            @Valid @RequestBody UpdateBoardRequest request
    ) {

        return ResponseEntity.ok(
                boardService.updateBoard(
                        organizationId,
                        projectId,
                        boardId,
                        request
                )
        );
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoardById(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID boardId
    ) {

        boardService.deleteBoardById(
                organizationId,
                projectId,
                boardId
        );

        return ResponseEntity.noContent().build();
    }
}