package com.github.lohithpuvvala.projectmanagement.backend.project.repository;

import com.github.lohithpuvvala.projectmanagement.backend.organization.entity.Organization;
import com.github.lohithpuvvala.projectmanagement.backend.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ProjectRespository extends JpaRepository<Project, UUID> {
    boolean existsByOrganizationAndName(Organization organization, String name);
    Page<Project> findByOrganization(Organization organization, Pageable pageable);
    Optional<Project> findByIdAndOrganization(UUID id, Organization organization);
}
