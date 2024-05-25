package com.pgmanagement.application.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class Rent {

    private long rentId;
    private boolean isPending;
    private double rentPaid;
    private double referalsDiscount;
    private double totalRent;
    private LocalDate createdDate;
    private long settledAgainst;
    private RentReceipt rentReceipt;
    private LocalDate rentDueDate;
    private String emailId;
    private String phoneNumber;
}
