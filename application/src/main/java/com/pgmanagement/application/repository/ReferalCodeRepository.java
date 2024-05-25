package com.pgmanagement.application.repository;


import com.pgmanagement.application.dao.ReferalCodes;
import com.pgmanagement.application.entity.ReferalCodesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferalCodeRepository extends JpaRepository<ReferalCodesEntity,Long> {

    ReferalCodesEntity findByappuserFK(long appuserFK);

    ReferalCodesEntity findByreferalCode(String referalCode);
}
