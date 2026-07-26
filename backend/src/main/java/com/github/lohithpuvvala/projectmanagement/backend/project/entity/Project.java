package com.github.lohithpuvvala.projectmanagement.backend.project.entity;

import com.github.lohithpuvvala.projectmanagement.backend.board.entity.Board;
import com.github.lohithpuvvala.projectmanagement.backend.common.entity.BaseEntity;
import com.github.lohithpuvvala.projectmanagement.backend.organization.entity.Organization;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "projects",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"organization_id","name"}
                )
        }
)
public class Project extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "organization_id",
            nullable = false
    )
    private Organization organization;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Board> boards = new ArrayList<>();
}
