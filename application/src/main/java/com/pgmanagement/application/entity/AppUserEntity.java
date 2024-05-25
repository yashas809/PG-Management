package com.pgmanagement.application.entity;

import com.pgmanagement.application.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name="appusers")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppUserPK")
    private long AppUserPK;

    @Column(name = "userfirstname")
    private String firstname;

    @Column(name = "userlastname")
    private String lastname;

    @Column(name = "emailid")
    private String emailId;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "LoginFK")
    private long loginFK;

    @Column(name = "RoleFK")
    private long roleFK;

    @Column(name = "createddate")
    private LocalDate createddate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "advtId")
    private long advtId;
}