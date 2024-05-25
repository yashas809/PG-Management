package com.pgmanagement.application.repository;

import com.pgmanagement.application.entity.ReferalBonusEntity;
import com.pgmanagement.application.enums.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReferalBonusRepository extends JpaRepository<ReferalBonusEntity,Long> {

    Optional<List<ReferalBonusEntity>> findByReferalFromAndClaimStatus(long referalFrom, ClaimStatus claimStatus);
}
