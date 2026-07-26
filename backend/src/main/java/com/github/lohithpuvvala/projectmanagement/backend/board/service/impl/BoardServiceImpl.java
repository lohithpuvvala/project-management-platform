package com.github.lohithpuvvala.projectmanagement.backend.board.service.impl;

import com.github.lohithpuvvala.projectmanagement.backend.board.dto.request.CreateBoardRequest;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.request.UpdateBoardRequest;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.dto.response.BoardSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.board.entity.Board;
import com.github.lohithpuvvala.projectmanagement.backend.board.mapper.BoardMapper;
import com.github.lohithpuvvala.projectmanagement.backend.board.repository.BoardRepository;
import com.github.lohithpuvvala.projectmanagement.backend.board.service.BoardService;
import com.github.lohithpuvvala.projectmanagement.backend.common.exception.IllegalOperationException;
import com.github.lohithpuvvala.projectmanagement.backend.common.exception.ResourceAlreadyExistsException;
import com.github.lohithpuvvala.projectmanagement.backend.common.exception.ResourceNotFoundException;
import com.github.lohithpuvvala.projectmanagement.backend.common.response.PagedResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.entity.Organization;
import com.github.lohithpuvvala.projectmanagement.backend.organization.repository.OrganizationRepository;
import com.github.lohithpuvvala.projectmanagement.backend.project.entity.Project;
import com.github.lohithpuvvala.projectmanagement.backend.project.repository.ProjectRespository;
import com.github.lohithpuvvala.projectmanagement.backend.security.SecurityUtils;
import com.github.lohithpuvvala.projectmanagement.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final OrganizationRepository organizationRepository;
    private final ProjectRespository projectRepository;
    private final SecurityUtils securityUtils;
    private final BoardMapper boardMapper;

    private Project findProject(UUID organizationId, UUID projectId) {
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = organizationRepository
                .findByIdAndOwner(organizationId, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization", organizationId));

        Project project = projectRepository
                .findByIdAndOrganization(projectId, organization)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project", projectId));

        return project;
    }

    private Board buildBoard(
            Project project,
            String name,
            boolean systemBoard
    ) {
        return Board.builder()
                .name(name)
                .systemBoard(systemBoard)
                .project(project)
                .build();
    }

    private Board findBoard(Project project, UUID boardId) {
        Board board = boardRepository
                .findByIdAndProject(boardId, project)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Board", boardId));
        return board;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<BoardSummaryResponse> getBoards(
            UUID organizationId,
            UUID projectId,
            Pageable pageable
    ) {

        Project project = findProject(organizationId, projectId);

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "name")
        );

        Page<Board> boardsPage = boardRepository.findByProject(project, pageable);

        List<BoardSummaryResponse> content = boardsPage
                .getContent()
                .stream()
                .map(boardMapper::toSummaryResponse)
                .toList();


        return PagedResponse.from(boardsPage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardDetailResponse getBoardById(
            UUID organizationId,
            UUID projectId,
            UUID boardId
    ) {

        Project project = findProject(organizationId, projectId);

        Board board = findBoard(project, boardId);

        return boardMapper.toDetailResponse(board);
    }

    @Override
    @Transactional
    public BoardDetailResponse updateBoard(
            UUID organizationId,
            UUID projectId,
            UUID boardId,
            UpdateBoardRequest request
    ) {

        Project project = findProject(organizationId, projectId);

        Board board = findBoard(project, boardId);

        if (board.isSystemBoard()) {
            throw new IllegalOperationException(
                    "System boards cannot be renamed."
            );
        }

        if (!board.getName().equals(request.name())
                && boardRepository.existsByProjectAndName(project, request.name())) {

            throw new ResourceAlreadyExistsException(
                    "Board",
                    "name",
                    request.name()
            );
        }

        board.setName(request.name());

        boardRepository.flush();

        return boardMapper.toDetailResponse(board);
    }

    @Override
    @Transactional
    public void deleteBoardById(
            UUID organizationId,
            UUID projectId,
            UUID boardId
    ) {

        Project project = findProject(organizationId, projectId);

        Board board = findBoard(project, boardId);

        if (board.isSystemBoard()) {
            throw new IllegalOperationException(
                    "System boards cannot be deleted."
            );
        }

        boardRepository.delete(board);
    }

    @Override
    @Transactional
    public BoardCreateResponse createBoard(UUID organizationId, UUID projectId, CreateBoardRequest request) {
        Project project = findProject(organizationId, projectId);

        if (boardRepository.existsByProjectAndName(project, request.name())) {
            throw new ResourceAlreadyExistsException(
                    "Board",
                    "name",
                    request.name()
            );
        }

        Board board = buildBoard(
                project,
                request.name(),
                false
        );

        return boardMapper.toCreateResponse(boardRepository.save(board));
    }

    @Override
    @Transactional
    public void createDefaultBoards(Project project) {
        List<Board> boards = List.of(
                buildBoard(project, "To Do", true),
                buildBoard(project, "In Progress", true),
                buildBoard(project, "Done", true)
        );
        boardRepository.saveAll(boards);
    }
}
