package com.github.lohithpuvvala.projectmanagement.backend.organization.entity;

import com.github.lohithpuvvala.projectmanagement.backend.common.entity.BaseEntity;
import com.github.lohithpuvvala.projectmanagement.backend.project.entity.Project;
import com.github.lohithpuvvala.projectmanagement.backend.user.entity.User;
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
@Table(name = "organizations")
public class Organization extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Project> projects = new ArrayList<>();
}
