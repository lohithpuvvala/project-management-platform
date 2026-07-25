package com.github.lohithpuvvala.projectmanagement.backend.project.entity;

import com.github.lohithpuvvala.projectmanagement.backend.common.entity.BaseEntity;
import com.github.lohithpuvvala.projectmanagement.backend.organization.entity.Organization;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
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
}
