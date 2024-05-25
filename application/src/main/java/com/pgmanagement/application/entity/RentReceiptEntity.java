package com.pgmanagement.application.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "rentreceipt")
@Data
public class RentReceiptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentReceiptPK")
    private long feeReceiptPK;

    @Column(name = "Receipt")
    @Lob
    private byte[] receiptFile;

    @Column(name = "fileName")
    private String fileName;
}
