package com.pgmanagement.application.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class Registration {

    private long registrationId;
    private String referalCode;
    private String adharNumber;
    private String permanentAddress;
}
