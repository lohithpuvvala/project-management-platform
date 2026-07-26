package com.github.lohithpuvvala.projectmanagement.backend.project.service;

import com.github.lohithpuvvala.projectmanagement.backend.board.service.BoardService;
import com.github.lohithpuvvala.projectmanagement.backend.common.exception.ResourceAlreadyExistsException;
import com.github.lohithpuvvala.projectmanagement.backend.common.exception.ResourceNotFoundException;
import com.github.lohithpuvvala.projectmanagement.backend.common.response.PagedResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.entity.Organization;
import com.github.lohithpuvvala.projectmanagement.backend.organization.repository.OrganizationRepository;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.request.CreateProjectRequest;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.request.UpdateProjectRequest;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.dto.response.ProjectSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.project.entity.Project;
import com.github.lohithpuvvala.projectmanagement.backend.project.mapper.ProjectMapper;
import com.github.lohithpuvvala.projectmanagement.backend.project.repository.ProjectRespository;
import com.github.lohithpuvvala.projectmanagement.backend.security.SecurityUtils;
import com.github.lohithpuvvala.projectmanagement.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRespository projectRepository;
    private final SecurityUtils securityUtils;
    private final OrganizationRepository organizationRepository;
    private final ProjectMapper projectMapper;
    private final BoardService boardService;

    @Override
    @Transactional
    public ProjectCreateResponse createProject(
            UUID organizationId,
            CreateProjectRequest request){
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = organizationRepository.findByIdAndOwner(organizationId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", organizationId));

        if(projectRepository.existsByOrganizationAndName(organization,request.name())){
            throw new ResourceAlreadyExistsException("Project","name", request.name());
        }

        Project project = Project.builder()
                .name(request.name())
                .description(request.description())
                .organization(organization)
                .build();

        Project savedProject = projectRepository.save(project);

        boardService.createDefaultBoards(project);

        return projectMapper.toCreateResponse(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ProjectSummaryResponse> getProjects(UUID organizationId,Pageable pageable) {
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = organizationRepository.findByIdAndOwner(organizationId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", organizationId));

        Page<Project> projectsPage = projectRepository.findByOrganization(organization, pageable);

        List<ProjectSummaryResponse> content = projectsPage.getContent()
                .stream()
                .map(projectMapper::toSummaryResponse)
                .toList();

        return PagedResponse.from(projectsPage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDetailResponse getProjectById(UUID organizationId, UUID projectId) {
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = organizationRepository.findByIdAndOwner(organizationId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", organizationId));

        Project project = projectRepository.findByIdAndOrganization(projectId, organization)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId));

        return projectMapper.toDetailResponse(project);
    }

    @Override
    @Transactional
    public ProjectDetailResponse updateProject(UUID organizationId, UUID projectId, UpdateProjectRequest request) {
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = organizationRepository.findByIdAndOwner(organizationId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", organizationId));

        Project project = projectRepository.findByIdAndOrganization(projectId, organization)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId));

        if (!project.getName().equals(request.name())
                && projectRepository.existsByOrganizationAndName(
                organization,
                request.name())) {

            throw new ResourceAlreadyExistsException(
                    "Project",
                    "name",
                    request.name()
            );
        }
        project.setName(request.name());
        project.setDescription(request.description());

        projectRepository.flush();

        return projectMapper.toDetailResponse(project);
    }

    @Override
    @Transactional
    public void deleteProjectById(UUID organizationId, UUID projectId) {
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = organizationRepository.findByIdAndOwner(organizationId,currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", organizationId));

        Project project = projectRepository.findByIdAndOrganization(projectId, organization)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId));

        projectRepository.delete(project);
    }
}
