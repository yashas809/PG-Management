package com.pgmanagement.application.service;

import com.pgmanagement.application.dao.AdvertisementInfo;
import com.pgmanagement.application.entity.PgAdvertisement;
import com.pgmanagement.application.repository.PGAdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IPGAdvertismentImpl implements IAdvertisementService{

    @Autowired
    PGAdvertisementRepository pgAdvertisementRepository;

    @Override
    public boolean add(AdvertisementInfo request) {
        try{
            PgAdvertisement entity = PgAdvertisement.build(0L, request.getRoomNo(), request.getFloorNo(),
                    request.getFoodType(), request.getNoOfShares(), request.getVacancy(), request.isAvailable());
            pgAdvertisementRepository.save(entity);
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(long advertisementId, AdvertisementInfo request) {
        try {
            Optional<PgAdvertisement> optionalPgAdvertisement = pgAdvertisementRepository.findById(advertisementId);
            if (optionalPgAdvertisement.isPresent()) {
                PgAdvertisement entity = optionalPgAdvertisement.get();
                if (request.isAvailable()) {
                    entity.setAvailable(true);
                }
                if (request.getNoOfShares() != 0L || request.getVacancy() != 0L) {
                    if (request.getNoOfShares() != 0L) {
                        entity.setNoofSharing(request.getNoOfShares());
                    }
                    if (request.getVacancy() != 0L) {
                        entity.setVacancy(request.getVacancy());
                    }
                    if (request.getVacancy()==0L) {
                        entity.setAvailable(false);
                    }
                }
                if(request.getRoomNo()!=0L)
                {
                    entity.setRoomNo(request.getRoomNo());
                }
                if(request.getFloorNo()!=0L)
                {
                    entity.setFloor(request.getFloorNo());
                }
                if (request.getFoodType() != null) {
                    entity.setFoodType(request.getFoodType());
                }
                pgAdvertisementRepository.saveAndFlush(entity);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<AdvertisementInfo> getAvailableAdvertisement() {
        List<AdvertisementInfo> response = new ArrayList<>();
        List<PgAdvertisement> pgAdvertisementList = pgAdvertisementRepository.findByAvailable(true);
        for(PgAdvertisement pg : pgAdvertisementList)
        {
            response.add(AdvertisementInfo.build(pg.getAdvertisementID(),pg.getRoomNo(), pg.getFloor(), pg.getFoodType(), pg.getNoofSharing(), pg.getVacancy(),pg.isAvailable()));
        }
        return response;
    }

    @Override
    public List<AdvertisementInfo> getAllAdvertisment() {
        List<AdvertisementInfo> response = new ArrayList<>();
        List<PgAdvertisement> pgAdvertisementList = pgAdvertisementRepository.findAll();
        for(PgAdvertisement pg : pgAdvertisementList)
        {
            response.add(AdvertisementInfo.build(pg.getAdvertisementID(),pg.getRoomNo(), pg.getFloor(), pg.getFoodType(), pg.getNoofSharing(), pg.getVacancy(),pg.isAvailable()));
        }
        return response;
    }

    @Override
    public AdvertisementInfo getAdvertisment(long id) {
       Optional<PgAdvertisement> optionalPgAdvertisement = pgAdvertisementRepository.findById(id);
       if(optionalPgAdvertisement.isPresent())
       {
           PgAdvertisement pg = optionalPgAdvertisement.get();
           return AdvertisementInfo.build(pg.getAdvertisementID(),pg.getRoomNo(), pg.getFloor(), pg.getFoodType(), pg.getNoofSharing(), pg.getVacancy(),pg.isAvailable());
       }
        return null;
    }
}
