package com.pgmanagement.application.repository;

import com.pgmanagement.application.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginEntity, Long> {

    LoginEntity findByloginPK(long loginPK);

    LoginEntity findByloginName(String loginName);

}