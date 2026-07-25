package com.github.lohithpuvvala.projectmanagement.backend.organization.controller;

import com.github.lohithpuvvala.projectmanagement.backend.common.response.PagedResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.request.CreateOrganizationRequest;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.request.UpdateOrganizationRequest;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationCreateResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationDetailResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.dto.response.OrganizationSummaryResponse;
import com.github.lohithpuvvala.projectmanagement.backend.organization.service.impl.OrganizationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationServiceImpl organizationService;

    @PostMapping
    public ResponseEntity<OrganizationCreateResponse> createOrganization(
            @Valid @RequestBody CreateOrganizationRequest createOrganizationRequest) {
        OrganizationCreateResponse response = organizationService.createOrganization(createOrganizationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<OrganizationSummaryResponse>> getAllOrganizations(Pageable pageable) {
        return ResponseEntity.ok(organizationService.getOrganizations(pageable));
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationDetailResponse> getOrganizationById(@PathVariable UUID organizationId) {
        return ResponseEntity.ok(organizationService.getOrganizationById(organizationId));
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<OrganizationDetailResponse> updateOrganization(
            @PathVariable UUID organizationId,
            @Valid @RequestBody UpdateOrganizationRequest request) {
        return ResponseEntity.ok(organizationService.updateOrganization(organizationId,request));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable UUID organizationId) {

        organizationService.deleteOrganizationById(organizationId);

        return ResponseEntity.noContent().build();
    }
}
