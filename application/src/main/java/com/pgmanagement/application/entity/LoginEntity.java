package com.pgmanagement.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "login")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
public class LoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LoginPK")
    private Long loginPK;

    @Column(name = "LoginName")
    private String loginName;

    @Column(name = "Password")
    private String password;

    @Column(name = "Createddate")
    private LocalDateTime createddate;

    @Column(name = "DelFlag")
    private Boolean delFlag;

    @Column(name = "SecretKey")
    private String secretKey;


}