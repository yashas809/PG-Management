package com.pgmanagement.application.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "role")
@Entity
@NoArgsConstructor
@Data
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rolepk")
    private long rolepk;

    @Column(name = "rolename")
    private String roleName;
}