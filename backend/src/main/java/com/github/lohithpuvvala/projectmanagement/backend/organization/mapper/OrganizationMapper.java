package com.github.lohithpuvvala.projectmanagement.backend.organization.mapper;

import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationCreateResponse toCreateResponse(Organization organization) {
        return  new OrganizationCreateResponse(
                organization.getId(),
                organization.getName(),
                organization.getDescription()
        );
    }

    public OrganizationSummaryResponse toSummaryResponse(Organization organization) {
        return  new OrganizationSummaryResponse(
                organization.getId(),
                organization.getName()
        );
    }

    public OrganizationDetailResponse toDetailedResponse(Organization organization) {
        return  new OrganizationDetailResponse(
                organization.getId(),
                organization.getName(),
                organization.getDescription(),
                organization.getCreatedAt(),
                organization.getUpdatedAt()
        );
    }
}
