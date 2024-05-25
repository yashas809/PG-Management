package com.pgmanagement.application.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class RentReceipt {

    private long receiptId;
    private byte[] receipt;
    private String fileName;
}
