package com.pgmanagement.application.dao;

import com.pgmanagement.application.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class AppUser {

    private String firstname;
    private String lastname;
    private String emailId;
    private String phoneNumber;
    private String loginname;
    private String password;
    private String roleName;
    private Gender gender;
    private LocalDate createddate;
    private long Id;
    private Registration registration;
    private Approvals approvals;
    private ReferalCodes referalCodes;
    private List<ReferalBonus> referalBonus;
    private AdvertisementInfo advertisementInfo;
}
