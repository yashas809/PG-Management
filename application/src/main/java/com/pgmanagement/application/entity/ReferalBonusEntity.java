package com.pgmanagement.application.entity;

import com.pgmanagement.application.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "referals")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class ReferalBonusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "referalPK")
    private long referalPK;

    @Column(name = "referalfrom")
    private long referalFrom;

    @Column(name = "ReferalTo")
    private long ReferalTo;

    @Column(name = "claimStatus")
    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;

    @Column(name = "discountAmount")
    private double discountAmount;
}
