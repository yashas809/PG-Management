package com.pgmanagement.application.repository;

import com.pgmanagement.application.entity.ApprovalsEntity;
import com.pgmanagement.application.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApprovalsRepository extends JpaRepository<ApprovalsEntity, Long> {

    public Optional<ApprovalsEntity> findByuserFK(long userFK);

    public Optional<List<ApprovalsEntity>> findByreviewStatus(ApprovalStatus status);
}
