package com.pgmanagement.application.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class login {
    private String loginname;
    private String password;
    private String secretkey;

}