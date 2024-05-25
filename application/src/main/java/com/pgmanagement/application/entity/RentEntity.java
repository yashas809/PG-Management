package com.pgmanagement.application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "rent")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class RentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentPK")
    private long rentPK;

    @Column(name ="appUserFK")
    private long appUserFK;

    @Column(name ="IsPending")
    private boolean isPending;

    @Column(name ="ReceiptFK")
    private long receiptFK;

    @Column(name ="RentPaid")
    private double RentPaid;

    @Column(name ="referalsdiscount")
    private double referalsdiscount;

    @Column(name ="totalRent")
    private double totalRent;

    @Column(name ="CreatedDate")
    private LocalDate CreatedDate;

    @Column(name ="referalsFK")
    private long referalsFK;

    @Column(name ="RentDueData")
    private LocalDate rentDueData;
}
