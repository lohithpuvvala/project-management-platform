package com.github.lohithpuvvala.projectmanagement.backend.organization.repository;

import com.github.lohithpuvvala.projectmanagement.backend.organization.entity.Organization;
import com.github.lohithpuvvala.projectmanagement.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Page<Organization> findByOwner(User Owner,Pageable pageable);
    Optional<Organization> findByIdAndOwner(UUID id,User Owner);
}
