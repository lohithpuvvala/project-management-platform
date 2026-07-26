package com.github.lohithpuvvala.projectmanagement.backend.board.entity;

import com.github.lohithpuvvala.projectmanagement.backend.common.entity.BaseEntity;
import com.github.lohithpuvvala.projectmanagement.backend.project.entity.Project;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "boards",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"project_id","name"}
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity {
    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean systemBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
