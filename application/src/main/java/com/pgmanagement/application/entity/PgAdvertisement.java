package com.pgmanagement.application.entity;

import com.pgmanagement.application.enums.FoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name ="pgadvertisement")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class PgAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdvertisementID")
    private long advertisementID;

    @Column(name = "RoomNo")
    private long roomNo;

    @Column(name = "Floor")
    private long floor;

    @Column(name = "FoodType")
    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @Column(name = "NoofSharing")
    private long noofSharing;

    @Column(name = "vacancy")
    private long vacancy;

    @Column(name = "Available")
    private boolean available;
}
