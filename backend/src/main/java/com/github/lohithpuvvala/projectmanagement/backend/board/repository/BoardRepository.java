package com.github.lohithpuvvala.projectmanagement.backend.board.repository;

import com.github.lohithpuvvala.projectmanagement.backend.board.entity.Board;
import com.github.lohithpuvvala.projectmanagement.backend.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
    boolean existsByProjectAndName(Project project, String name);
    Page<Board> findByProject(Project project, Pageable pageable);
    Optional<Board> findByIdAndProject(UUID id, Project project);
}
