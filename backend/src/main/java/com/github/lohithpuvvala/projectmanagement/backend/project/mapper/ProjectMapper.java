package com.github.lohithpuvvala.projectmanagement.backend.project.mapper;

import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public ProjectCreateResponse toCreateResponse(Project project){
        return new ProjectCreateResponse(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
    }

    public ProjectSummaryResponse toSummaryResponse(Project project){
        return new ProjectSummaryResponse(
                project.getId(),
                project.getName()
        );
    }

    public ProjectDetailResponse toDetailResponse(Project project){
        return new ProjectDetailResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
