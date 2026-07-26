package com.github.lohithpuvvala.projectmanagement.backend.board.mapper;

import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.entity.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public BoardCreateResponse toCreateResponse(Board board){
        return new BoardCreateResponse(
                board.getId(),
                board.getName()
        );
    }

    public BoardSummaryResponse toSummaryResponse(Board board){
        return new BoardSummaryResponse(
                board.getId(),
                board.getName()
        );
    }

    public BoardDetailResponse toDetailResponse(Board board){
        return new BoardDetailResponse(
                board.getId(),
                board.getName(),
                board.isSystemBoard(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }
}
