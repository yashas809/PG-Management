package com.pgmanagement.application.entity;

import com.pgmanagement.application.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "approvals")
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ApprovalsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approvalPK")
    private long approvalPK;

    @Column(name = "userFK")
    private long userFK;

    @Column(name = "reviewstatus")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus reviewStatus;
}
