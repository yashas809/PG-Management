package com.pgmanagement.application.dao;

import com.pgmanagement.application.enums.Reason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class CommunicationCase {

    private Reason reasonCode;
    private String userName;
    private String message;
    private char messageDirection;
}