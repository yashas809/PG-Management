package com.pgmanagement.application.service;

import com.pgmanagement.application.dao.AppUser;
import com.pgmanagement.application.entity.*;
import com.pgmanagement.application.enums.ApprovalStatus;
import com.pgmanagement.application.enums.ClaimStatus;
import com.pgmanagement.application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class IApprovalServiceImpl implements IApprovalService{

    @Autowired
    PGAdvertisementRepository pgAdvertisementRepository;

    @Autowired
    ApprovalsRepository approvalsRepository;

    @Autowired
    AppuserRepository appuserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ReferalCodeRepository referalCodeRepository;

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    ReferalBonusRepository referalBonusRepository;

    @Override
    public void SendForApproval(long appUserFK) {
        if (appUserFK != 0L) {
            ApprovalsEntity approvalsEntity = ApprovalsEntity.build(0L, appUserFK, ApprovalStatus.pending);
            approvalsRepository.save(approvalsEntity);
        }
    }

    @Override
    public boolean actionItem(long appUserFK,ApprovalStatus status) {
        Optional<ApprovalsEntity> optionalApprovalsEntity = approvalsRepository.findByuserFK(appUserFK);
        if(optionalApprovalsEntity.isPresent())
        {
            ApprovalsEntity approvalsEntity = optionalApprovalsEntity.get();
            approvalsEntity.setReviewStatus(status);
            approvalsRepository.saveAndFlush(approvalsEntity);
            if(status.equals(ApprovalStatus.approved))
            {

                updateUserRole(appUserFK);
                addReferalCode(appUserFK);
                Optional<RegistrationEntity> registrationEntity = registrationRepository.findByappuserFK(appUserFK);
                if(registrationEntity.isPresent() && (registrationEntity.get().getReferals()!=null))
                {
                    verifyAndReward(registrationEntity.get().getReferals(), appUserFK);
                }
            }else
            {
                reject(appUserFK);
            }
            return true;
        }
        return false;
    }

    private void updateUserRole(long appUserFK)
    {
        Optional<AppUserEntity> optionalAppUserEntity = appuserRepository.findById(appUserFK);
        if(optionalAppUserEntity.isPresent())
        {
            AppUserEntity appUserEntity = optionalAppUserEntity.get();
            appUserEntity.setRoleFK(roleRepository.findByroleName("Tenant").get().getRolepk());
            appuserRepository.saveAndFlush(appUserEntity);
            updateAdvertisement(appUserEntity.getAdvtId());
        }
    }

    private void reject(long appUserFK)
    {
        Optional<AppUserEntity> optionalAppUserEntity = appuserRepository.findById(appUserFK);
        if(optionalAppUserEntity.isPresent())
        {
            AppUserEntity appUserEntity = optionalAppUserEntity.get();
            appUserEntity.setRoleFK(roleRepository.findByroleName("Deleted").get().getRolepk());
            appuserRepository.saveAndFlush(appUserEntity);
        }
    }

    private void addReferalCode(long appUserFK)
    {
        referalCodeRepository.save(ReferalCodesEntity.build(0L,this.generateReferalCode(), appUserFK));
    }

    protected String generateReferalCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

    private void verifyAndReward(String referalCode, long appUserfk)
    {
       ReferalCodesEntity referalCodesEntity = referalCodeRepository.findByreferalCode(referalCode);
       if(referalCodesEntity!=null)
       {
            referalBonusRepository.save(ReferalBonusEntity.build(0L,referalCodesEntity.getAppuserFK(),appUserfk, ClaimStatus.UnSettled,100));
       }
    }

    private void updateAdvertisement(long advertismentId)
    {
       Optional<PgAdvertisement> optionalEntity = pgAdvertisementRepository.findById(advertismentId);
       if(optionalEntity.isPresent())
       {
           PgAdvertisement pgAdvertisement = optionalEntity.get();
           pgAdvertisement.setVacancy(pgAdvertisement.getVacancy()-1);

           if(pgAdvertisement.getVacancy()==0L)
           {
               pgAdvertisement.setAvailable(false);
           }
           pgAdvertisementRepository.saveAndFlush(pgAdvertisement);
       }
    }
}
