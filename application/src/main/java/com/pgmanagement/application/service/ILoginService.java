package com.pgmanagement.application.service;

import com.pgmanagement.application.dao.login;
import com.pgmanagement.application.entity.LoginEntity;

public interface ILoginService
{
    public LoginEntity create(login request);

    public LoginEntity findByloginPK(Long loginPk);

    public LoginEntity findByloginName(String loginName);

    public boolean verifyPassword(String enteredPassword, String storedPassword, String salt);
}