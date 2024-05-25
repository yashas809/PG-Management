package com.pgmanagement.application.service;

import com.pgmanagement.application.dao.login;
import com.pgmanagement.application.entity.LoginEntity;
import com.pgmanagement.application.repository.LoginRepository;
import common.dao.Password;
import common.service.PwdUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ILoginServiceImpl implements ILoginService
{
    private final LoginRepository repository;
    @Autowired
    public ILoginServiceImpl(LoginRepository repository)
    {
        this.repository = repository;
    }
    @Override
    public LoginEntity create(login logindata) {
        try
        {
            Password utlityClass = PwdUtility.EncryptPassword(logindata.getPassword());
            logindata.setSecretkey(utlityClass.getSalt());
            logindata.setPassword(utlityClass.getEncryptedPassword());
            LoginEntity entityData = LoginEntity.build(0l,logindata.getLoginname(), logindata.getPassword(), LocalDateTime.now(),false, logindata.getSecretkey());
            entityData = repository.save(entityData);
            return entityData;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public LoginEntity findByloginPK(Long loginPk) {
        try
        {
            return repository.findByloginPK(loginPk);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public LoginEntity findByloginName(String loginName) {
        try{
            return repository.findByloginName(loginName);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean verifyPassword(String enteredPassword, String storedPassword, String salt) {
        try
        {
            return PwdUtility.Verify(enteredPassword,salt,storedPassword);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
}