package com.pgmanagement.application.dao;

import com.pgmanagement.application.enums.ClaimStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class ReferalBonus {

    private long referalBonusId;
    private ClaimStatus claimStatus;
    private String referedTo;
    private Double discountAmount;
}
