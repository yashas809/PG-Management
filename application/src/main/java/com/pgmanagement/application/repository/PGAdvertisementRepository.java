package com.pgmanagement.application.repository;

import com.pgmanagement.application.entity.PgAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PGAdvertisementRepository extends JpaRepository<PgAdvertisement,Long> {

    public List<PgAdvertisement> findByAvailable(boolean Available);


}
