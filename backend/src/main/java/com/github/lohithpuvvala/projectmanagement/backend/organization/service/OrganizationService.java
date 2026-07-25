package com.github.lohithpuvvala.projectmanagement.backend.organization.service;

import com.github.lohithpuvvala.projectmanagement.backend.common.response.PagedResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.request.CreateOrganizationRequest;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.request.UpdateOrganizationRequest;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationSummaryResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrganizationService {
    public OrganizationCreateResponse createOrganization(CreateOrganizationRequest request);
    public PagedResponse<OrganizationSummaryResponse> getOrganizations(Pageable pageable);
    public OrganizationDetailResponse getOrganizationById(UUID id);
    public OrganizationDetailResponse updateOrganization(UUID id, UpdateOrganizationRequest request);
    public void deleteOrganizationById(UUID id);
}
