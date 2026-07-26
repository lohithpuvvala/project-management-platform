package com.github.lohithpuvvala.projectmanagement.backend.board.service;

import com.github.lohithpuvvala.projectmanagement.backend.board.dto.request.CreateBoardRequest;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.request.UpdateBoardRequest;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.common.response.PagedResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.entity.Project;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BoardService {
    public PagedResponse<BoardSummaryResponse> getBoards(
            UUID organizationId,
            UUID projectId,
            Pageable pageable
    );

    public BoardDetailResponse getBoardById(
            UUID organizationId,
            UUID projectId,
            UUID boardId
    );

    public BoardDetailResponse updateBoard(
            UUID organizationId,
            UUID projectId,
            UUID boardId,
            UpdateBoardRequest request
    );

    public void deleteBoardById(
            UUID organizationId,
            UUID projectId,
            UUID boardId
    );

    BoardCreateResponse createBoard(
            UUID organizationId,
            UUID projectId,
            CreateBoardRequest request
    );

    void createDefaultBoards(Project project);
}
