package com.pgmanagement.application.dao;

import com.pgmanagement.application.enums.FoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class AdvertisementInfo {

    private long advertisementId;
    private long roomNo;
    private long floorNo;
    private FoodType foodType;
    private long noOfShares;
    private long vacancy;
    private boolean available;
}
