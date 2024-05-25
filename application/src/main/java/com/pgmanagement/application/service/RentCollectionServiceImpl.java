package com.pgmanagement.application.service;

import com.pgmanagement.application.dao.Rent;
import com.pgmanagement.application.dao.RentReceipt;
import com.pgmanagement.application.entity.AppUserEntity;
import com.pgmanagement.application.entity.ReferalBonusEntity;
import com.pgmanagement.application.entity.RentEntity;
import com.pgmanagement.application.entity.RentReceiptEntity;
import com.pgmanagement.application.enums.ClaimStatus;
import com.pgmanagement.application.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentCollectionServiceImpl implements RentCollectionService {

    @Autowired
    RentReceiptRepository rentReceiptRepository;

    @Autowired
    RentRepository rentRepository;

    @Autowired
    AppuserRepository appuserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ReferalBonusRepository referalBonusRepository;

    @Override
    public void collectRent(Rent request) {
        try {
            Optional<RentEntity> optionalRentEntity = rentRepository.findById(request.getRentId());
            if (optionalRentEntity.isPresent()) {
                RentEntity rentEntity = optionalRentEntity.get();
                rentEntity.setPending((request.getRentPaid() + request.getReferalsDiscount()) != rentEntity.getTotalRent());
                if (request.getRentPaid() != 0) {
                    rentEntity.setRentPaid(request.getRentPaid());
                }
                if (request.getReferalsDiscount() != 0) {
                    rentEntity.setReferalsdiscount(request.getReferalsDiscount());
                }
                if (request.getSettledAgainst() != 0) {
                    rentEntity.setReferalsFK(request.getSettledAgainst());
                    Optional<ReferalBonusEntity> referalBonusEntity = referalBonusRepository.findById(request.getSettledAgainst());
                    if (referalBonusEntity.isPresent()) {
                        ReferalBonusEntity referalBonus = referalBonusEntity.get();
                        referalBonus.setClaimStatus(ClaimStatus.Settled);
                        referalBonusRepository.saveAndFlush(referalBonus);
                    }
                }
                if (request.getRentReceipt() != null) {
                    RentReceiptEntity rentReceiptEntity = rentReceiptRepository.save(RentReceiptEntity.build(0L, request.getRentReceipt().getReceipt(), request.getRentReceipt().getFileName()));
                    rentEntity.setReceiptFK(rentReceiptEntity.getFeeReceiptPK());
                }
                rentRepository.saveAndFlush(rentEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void addRentReminder() {
        Optional<List<AppUserEntity>> optionalAppUserEntities = appuserRepository.findByroleFK(roleRepository.findByroleName("Tenant").get().getRolepk());
        if (optionalAppUserEntities.isPresent()) {
            List<AppUserEntity> list = optionalAppUserEntities.get();
            for (AppUserEntity entity : list) {
                LocalDate dueData = LocalDate.now().withDayOfMonth(1);
                Optional<RentEntity> rentEntity = rentRepository.checkifAlreadyScheduled(entity.getAppUserPK(), dueData);
                if (rentEntity.isEmpty()) {
                    RentEntity rentEntitys = RentEntity.build(0L, entity.getAppUserPK(), true, 0L, 0d, 0d, 2000, LocalDate.now(), 0L, dueData);
                    rentRepository.save(rentEntitys);
                }
            }
        }

    }

    @Override
    public List<Rent> viewRent(long appUserFK) {
        List<Rent> rent = new ArrayList<>();
        if (appuserRepository.findById(appUserFK).isPresent()) {
            Optional<List<RentEntity>> optionalRentEntityList = rentRepository.findByappUserFK(appUserFK);
            if (optionalRentEntityList.isPresent()) {
                List<RentEntity> rentEntityList = optionalRentEntityList.get();
                for (RentEntity rentEntity : rentEntityList) {
                    RentReceipt rentReceiptDAO = null;
                    if (rentEntity.getReceiptFK() != 0L) {
                        RentReceiptEntity rentReceiptEntity = rentReceiptRepository.findById(rentEntity.getReceiptFK()).get();
                        rentReceiptDAO = RentReceipt.build(rentReceiptEntity.getFeeReceiptPK(), rentReceiptEntity.getReceiptFile(), rentReceiptEntity.getFileName());
                    }
                    rent.add(Rent.build(rentEntity.getRentPK(), rentEntity.isPending(), rentEntity.getRentPaid(), rentEntity.getReferalsdiscount(), rentEntity.getTotalRent(), rentEntity.getCreatedDate(), rentEntity.getReferalsFK(),
                            rentReceiptDAO, rentEntity.getRentDueData(),appuserRepository.findById(rentEntity.getAppUserFK()).get().getEmailId(),
                            appuserRepository.findById(rentEntity.getAppUserFK()).get().getPhoneNumber()));
                }
            }
        }
        return rent;
    }


    @Override
    public List<Rent> viewAllRent() {
        List<Rent> rent = new ArrayList<>();
        List<RentEntity> rentEntityList = rentRepository.findAll();
        for (RentEntity rentEntity : rentEntityList) {
            RentReceipt rentReceiptDAO = null;
            if (rentEntity.getReceiptFK() != 0L) {
                RentReceiptEntity rentReceiptEntity = rentReceiptRepository.findById(rentEntity.getReceiptFK()).get();
                rentReceiptDAO = RentReceipt.build(rentReceiptEntity.getFeeReceiptPK(), rentReceiptEntity.getReceiptFile(), rentReceiptEntity.getFileName());
            }
            rent.add(Rent.build(rentEntity.getRentPK(), rentEntity.isPending(), rentEntity.getRentPaid(), rentEntity.getReferalsdiscount(), rentEntity.getTotalRent(), rentEntity.getCreatedDate(), rentEntity.getReferalsFK(), rentReceiptDAO,
                    rentEntity.getRentDueData(),
                    appuserRepository.findById(rentEntity.getAppUserFK()).get().getEmailId(),
                    appuserRepository.findById(rentEntity.getAppUserFK()).get().getPhoneNumber()));
        }
        return rent;
    }

}
