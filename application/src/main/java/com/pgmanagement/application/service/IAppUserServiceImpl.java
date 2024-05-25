package com.pgmanagement.application.service;

import com.pgmanagement.application.dao.*;
import com.pgmanagement.application.entity.*;
import com.pgmanagement.application.enums.ApprovalStatus;
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
public class IAppUserServiceImpl implements IAppUserService {

    private final ILoginService loginService;
    private final AppuserRepository appuserRepository;
    private final RoleRepository roleRepository;
    private final RegistrationRepository registrationRepository;
    private final ApprovalsRepository approvalsRepository;
    private final IApprovalService approvalService;
    private final ReferalCodeRepository referalCodeRepository;
    private final ReferalBonusRepository referalBonusRepository;
    private final PGAdvertisementRepository pgAdvertisementRepository;

    @Autowired
    public IAppUserServiceImpl(ILoginService loginService,
                               AppuserRepository appuserRepository,
                               RoleRepository roleRepository,
                               RegistrationRepository registrationRepository,
                               ApprovalsRepository approvalsRepository,IApprovalService approvalService,
                               ReferalCodeRepository referalCodeRepository,
                               ReferalBonusRepository referalBonusRepository,PGAdvertisementRepository pgAdvertisementRepository) {
        this.loginService = loginService;
        this.appuserRepository = appuserRepository;
        this.roleRepository = roleRepository;
        this.registrationRepository = registrationRepository;
        this.approvalsRepository = approvalsRepository;
        this.approvalService = approvalService;
        this.referalCodeRepository = referalCodeRepository;
        this.referalBonusRepository = referalBonusRepository;
        this.pgAdvertisementRepository = pgAdvertisementRepository;
    }

    @Override
    @Transactional
    public AppUser createAdmin(AppUser request) {
        if (request != null) {
            Optional<RoleEntity> optionalroleEntity = roleRepository.findByroleName(request.getRoleName());
            LoginEntity loginEntity = loginService.findByloginName(request.getLoginname());
            if (optionalroleEntity.isPresent() && loginEntity == null && (optionalroleEntity.get().getRoleName().equals("Owner"))) {
                loginEntity = loginService.create(login.build(request.getLoginname(), request.getPassword(), ""));
                if (loginEntity != null) {
                    AppUserEntity appUserEntity = AppUserEntity.build(0l, request.getFirstname(), request.getLastname(), request.getEmailId(), request.getPhoneNumber(),
                            loginEntity.getLoginPK(), optionalroleEntity.get().getRolepk(), LocalDate.now(), request.getGender(),0L);
                    appuserRepository.save(appUserEntity);
                    return request;
                }
            }
        }
        return null;
    }

    @Override
    public AppUser updateUser(AppUser request, String loginName) {
        if (loginName != null) {
            LoginEntity loginentity = loginService.findByloginName(loginName);
            if (loginentity != null) {
                Optional<AppUserEntity> optionalAppUserEntity = appuserRepository.findByloginFK(loginentity.getLoginPK());
                if (optionalAppUserEntity.isPresent()) {
                    AppUserEntity entityData = getAppUserEntity(request, optionalAppUserEntity.get());
                    appuserRepository.saveAndFlush(entityData);
                    return this.getUser(loginName);
                }
            }
        }
        return null;
    }

    private AppUserEntity getAppUserEntity(AppUser request, AppUserEntity entityData) {
        if (request.getGender() != null) {
            entityData.setGender(request.getGender());
        }
        if (request.getEmailId() != null) {
            entityData.setEmailId(request.getEmailId());
        }
        if (request.getFirstname() != null) {
            entityData.setFirstname(request.getFirstname());
        }
        if (request.getLastname() != null) {
            entityData.setLastname(request.getLastname());
        }
        if (request.getPhoneNumber() != null) {
            entityData.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getRoleName() != null) {
            entityData.setRoleFK(roleRepository.findByroleName(request.getRoleName()).get().getRolepk());
        }
        return entityData;
    }

    @Override
    public AppUser login(String loginname, String password) {
        if (loginname != null && password != null) {
            LoginEntity loginEntity = loginService.findByloginName(loginname);
            if (loginEntity != null) {
                if (loginService.verifyPassword(password, loginEntity.getPassword(), loginEntity.getSecretKey())) {
                    return this.getUser(loginname);
                }
            }
        }
        return null;
    }

    @Override
    public AppUser getUser(String loginname) {
        if (loginname != null) {
            LoginEntity loginEntity = loginService.findByloginName(loginname);
            if (loginEntity != null) {
                Optional<AppUserEntity> OptionalEntity = appuserRepository.findByloginFK(loginEntity.getLoginPK());
                if (OptionalEntity.isPresent()) {
                    AppUserEntity appUserEntity = OptionalEntity.get();
                    Registration registration = null;
                    Optional<RegistrationEntity> optionalRegistrationEntity = registrationRepository.findByappuserFK(appUserEntity.getAppUserPK());
                    if (optionalRegistrationEntity.isPresent()) {
                        RegistrationEntity registrationEntity = optionalRegistrationEntity.get();
                        registration = Registration.build(registrationEntity.getRegistrationPK(), registrationEntity.getReferals(), registrationEntity.getAdharNumber(), registrationEntity.getAddress());
                    }
                    Approvals approvals = null;
                    Optional<ApprovalsEntity> optionalApprovalsEntity = approvalsRepository.findByuserFK(appUserEntity.getAppUserPK());
                    if (optionalApprovalsEntity.isPresent()) {
                        ApprovalsEntity approvalsEntity = optionalApprovalsEntity.get();
                        approvals = Approvals.build(approvalsEntity.getApprovalPK(), approvalsEntity.getReviewStatus());
                    }
                    ReferalCodesEntity referalCodesEntity = referalCodeRepository.findByappuserFK(appUserEntity.getAppUserPK());
                    ReferalCodes referalCodes = null;
                    if(referalCodesEntity!=null)
                    {
                        referalCodes = ReferalCodes.build(referalCodesEntity.getReferalCode());
                    }
                    Optional<List<ReferalBonusEntity>> optionalReferalBonusEntities = referalBonusRepository.findByReferalFromAndClaimStatus(appUserEntity.getAppUserPK(), ClaimStatus.UnSettled);
                    List<ReferalBonus> referalBonusList = new ArrayList<>();
                    if(optionalReferalBonusEntities.isPresent())
                    {
                        List<ReferalBonusEntity> referalBonusEntityList = optionalReferalBonusEntities.get();
                        for(ReferalBonusEntity entity : referalBonusEntityList)
                        {
                            referalBonusList.add(ReferalBonus.build(entity.getReferalPK(),entity.getClaimStatus(),appuserRepository.findById(entity.getReferalTo()).get().getEmailId(), entity.getDiscountAmount()));
                        }
                    }
                    Optional<PgAdvertisement> optionalPgAdvertisement = null;
                    AdvertisementInfo advertisementInfo = null;
                    if( appUserEntity.getAdvtId()!=0l)
                    {
                        optionalPgAdvertisement = pgAdvertisementRepository.findById(appUserEntity.getAdvtId());
                        if(optionalPgAdvertisement.isPresent())
                        {
                            PgAdvertisement pgAdvertisement = optionalPgAdvertisement.get();
                            advertisementInfo = AdvertisementInfo.build(pgAdvertisement.getAdvertisementID(), pgAdvertisement.getRoomNo(), pgAdvertisement.getRoomNo(),pgAdvertisement.getFoodType(),pgAdvertisement.getNoofSharing(),pgAdvertisement.getVacancy(),pgAdvertisement.isAvailable());
                        }
                    }
                    return AppUser.build(appUserEntity.getFirstname(), appUserEntity.getLastname(), appUserEntity.getEmailId(), appUserEntity.getPhoneNumber(), loginname, "", roleRepository.findById(appUserEntity.getRoleFK()).get().getRoleName(), appUserEntity.getGender(), appUserEntity.getCreateddate(), appUserEntity.getAppUserPK(), registration,
                            approvals,referalCodes,referalBonusList,advertisementInfo);
                }
            }
        }
        return null;
    }

    @Override
    public List<AppUser> getAll() {
        List<AppUser> response = new ArrayList<>();
        List<AppUserEntity> optionalAppUserEntities = appuserRepository.findAll();
            for (AppUserEntity entity : optionalAppUserEntities) {
                if(roleRepository.findByroleName("Owner").get().getRolepk()!= entity.getRoleFK())
                {
                    String loginName = this.getLoginName(entity.getAppUserPK());
                    response.add(this.getUser(loginName));
                }
            }
        return response;
    }

    @Override
    public List<AppUser> getAllPendingUsers() {
        List<AppUser> appUsers = new ArrayList<>();
        Optional<List<ApprovalsEntity>> optionalApprovalsEntities = approvalsRepository.findByreviewStatus(ApprovalStatus.pending);
        if(optionalApprovalsEntities.isPresent())
        {
            List<ApprovalsEntity> approvalsEntities = optionalApprovalsEntities.get();
            for(ApprovalsEntity entiy : approvalsEntities)
            {
                String loginName = this.getLoginName(entiy.getUserFK());
                appUsers.add(this.getUser(loginName));
            }
        }
        return appUsers;
    }

    public String getLoginName(long appUserfk)
    {
        AppUserEntity appUserEntity = appuserRepository.findById(appUserfk).get();
        return  loginService.findByloginPK(appUserEntity.getLoginFK()).getLoginName();
    }
    @Transactional
    @Override
    public AppUser RegisterUser(AppUser request) {
        if (request != null) {
            Optional<RoleEntity> optionalroleEntity = roleRepository.findByroleName(request.getRoleName());
            LoginEntity loginEntity = loginService.findByloginName(request.getLoginname());
            if (optionalroleEntity.isPresent() && loginEntity == null) {
                if (!optionalroleEntity.get().getRoleName().equals("Owner")) {
                    loginEntity = loginService.create(login.build(request.getLoginname(), request.getPassword(), ""));
                    if (loginEntity != null) {
                        AppUserEntity appUserEntity = AppUserEntity.build(0l, request.getFirstname(), request.getLastname(), request.getEmailId(), request.getPhoneNumber(),
                                loginEntity.getLoginPK(), optionalroleEntity.get().getRolepk(), LocalDate.now(), request.getGender(),request.getAdvertisementInfo().getAdvertisementId());
                        appUserEntity = appuserRepository.save(appUserEntity);
                        if (request.getRegistration() != null && appUserEntity.getAppUserPK() != 0L) {
                            RegistrationEntity registrationEntity = RegistrationEntity.build(0L, request.getRegistration().getReferalCode(), request.getRegistration().getAdharNumber(), request.getRegistration().getPermanentAddress(), appUserEntity.getAppUserPK());
                            registrationRepository.save(registrationEntity);
                            approvalService.SendForApproval(appUserEntity.getAppUserPK());
                            return this.getUser(request.getLoginname());
                        }
                    }
                }
            }
        }
        return null;
    }

}

