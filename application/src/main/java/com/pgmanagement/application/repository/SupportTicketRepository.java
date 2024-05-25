package com.pgmanagement.application.repository;

import com.pgmanagement.application.entity.SupportTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicketEntity, Long> {

    List<SupportTicketEntity> findByappUserFK(long appUserfk);

    SupportTicketEntity findBycaseIDFK(long caseIDFK);
}
