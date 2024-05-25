package com.pgmanagement.application.repository;

import com.pgmanagement.application.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {

    public Optional<RegistrationEntity> findByappuserFK(long appUserFk);
}
