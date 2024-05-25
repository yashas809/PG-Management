package com.pgmanagement.application.repository;

import com.pgmanagement.application.entity.RentReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentReceiptRepository extends JpaRepository<RentReceiptEntity, Long> {

}
