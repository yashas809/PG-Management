package com.pgmanagement.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "referalcodes")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class ReferalCodesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "referalPK")
    private long referalPK;

    @Column(name = "referalCode")
    private String referalCode;

    @Column(name = "appuserFK")
    private long appuserFK;
}
