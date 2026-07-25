package com.github.lohithpuvvala.projectmanagement.backend.project.controller;

import com.github.lohithpuvvala.projectmanagement.backend.common.response.PagedResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.request.CreateProjectRequest;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.request.UpdateProjectRequest;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectCreateResponse> createProject(
            @PathVariable UUID organizationId,
            @Valid @RequestBody CreateProjectRequest request){
        ProjectCreateResponse response = projectService.createProject(organizationId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ProjectSummaryResponse>> getProjects(@PathVariable UUID organizationId, Pageable pageable){
        return ResponseEntity.ok(
                projectService.getProjects(organizationId, pageable)
        );
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> getProjectById(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId){
        return ResponseEntity.ok(
                projectService.getProjectById(organizationId, projectId)
        );
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> updateProject(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @Valid @RequestBody UpdateProjectRequest request){
        return ResponseEntity.ok(
                projectService.updateProject(organizationId,projectId,request)
        );
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId
    ){
        projectService.deleteProjectById(organizationId, projectId);
        return ResponseEntity.noContent().build();
    }
}
