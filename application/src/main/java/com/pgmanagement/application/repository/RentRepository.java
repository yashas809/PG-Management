package com.pgmanagement.application.repository;

import com.pgmanagement.application.entity.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<RentEntity, Long> {

    public Optional<List<RentEntity>> findByappUserFK(long appUserFK);

    @Procedure(value = "checkifAlreadyScheduled")
    Optional<RentEntity> checkifAlreadyScheduled(@Param(value = "appUserFk") long appUserFk,
                                                 @Param(value = "rentDueDate") LocalDate rentDueDate
                                                );
}
