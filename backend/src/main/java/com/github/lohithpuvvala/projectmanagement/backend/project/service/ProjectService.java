package com.github.lohithpuvvala.projectmanagement.backend.project.service;

import com.github.lohithpuvvala.projectmanagement.backend.common.response.PagedResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.request.CreateProjectRequest;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.request.UpdateProjectRequest;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectSummaryResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProjectService {

    ProjectCreateResponse createProject(
            UUID organizationId,
            CreateProjectRequest request
    );

    PagedResponse<ProjectSummaryResponse> getProjects(
            UUID organizationId,
            Pageable pageable
    );

    ProjectDetailResponse getProjectById(
            UUID organizationId,
            UUID projectId
    );

    ProjectDetailResponse updateProject(
            UUID organizationId,
            UUID projectId,
            UpdateProjectRequest request
    );

    void deleteProjectById(
            UUID organizationId,
            UUID projectId
    );
}