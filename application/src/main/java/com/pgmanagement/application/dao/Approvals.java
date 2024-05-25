package com.pgmanagement.application.dao;

import com.pgmanagement.application.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class Approvals {

    private long id;
    private ApprovalStatus approvalStatus;
}
