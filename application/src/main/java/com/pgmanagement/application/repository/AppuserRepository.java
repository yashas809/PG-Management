package com.pgmanagement.application.repository;


import com.pgmanagement.application.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppuserRepository extends JpaRepository<AppUserEntity,Long> {

    Optional<AppUserEntity> findByloginFK(long LoginFK);

    Optional<List<AppUserEntity>> findByroleFK(long roleFK);
}