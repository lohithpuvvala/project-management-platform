package com.github.lohithpuvvala.projectmanagement.backend.organization.service.impl;

import com.github.lohithpuvvala.projectmanagement.backend.common.exception.ResourceNotFoundException;
import com.github.lohithpuvvala.projectmanagement.backend.common.response.PagedResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.request.CreateOrganizationRequest;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.request.UpdateOrganizationRequest;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.entity.Organization;
import com.github.lohithpuvvala.projectmanagement.backend.organization.mapper.OrganizationMapper;
import com.github.lohithpuvvala.projectmanagement.backend.organization.repository.OrganizationRepository;
import com.github.lohithpuvvala.projectmanagement.backend.organization.service.OrganizationService;
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
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final SecurityUtils securityUtils;
    private final OrganizationMapper organizationMapper;

    @Override
    @Transactional
    public OrganizationCreateResponse createOrganization(CreateOrganizationRequest request){
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = Organization.builder()
                .name(request.name())
                .description(request.description())
                .owner(currentUser)
                .build();

        Organization savedOrganization = organizationRepository.save(organization);

        return organizationMapper.toCreateResponse(savedOrganization);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<OrganizationSummaryResponse> getOrganizations(Pageable pageable){
        User currentUser = securityUtils.getCurrentUser();

        Page<Organization> OrganizationPage = organizationRepository.findByOwner(currentUser, pageable);

        List<OrganizationSummaryResponse> organizations = OrganizationPage.getContent()
                .stream()
                .map(organizationMapper::toSummaryResponse)
                .toList();

        return PagedResponse.from(OrganizationPage, organizations);
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationDetailResponse getOrganizationById(UUID id){
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = organizationRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        return organizationMapper.toDetailedResponse(organization);
    }

    @Override
    @Transactional
    public OrganizationDetailResponse updateOrganization(UUID id, UpdateOrganizationRequest request){
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = organizationRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        organization.setName(request.name());
        organization.setDescription(request.description());

        organizationRepository.flush();

        return organizationMapper.toDetailedResponse(organization);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(UUID id){
        User currentUser = securityUtils.getCurrentUser();

        Organization organization = organizationRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        organizationRepository.delete(organization);
    }
}
