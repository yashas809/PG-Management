package com.pgmanagement.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "registration")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registrationPK")
    private long registrationPK;

    @Column(name = "referals")
    private String referals;

    @Column(name = "adharNumber")
    private String adharNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "appuserFK")
    private long appuserFK;
}
