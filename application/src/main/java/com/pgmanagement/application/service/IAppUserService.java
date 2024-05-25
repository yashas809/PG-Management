package com.pgmanagement.application.service;

import com.pgmanagement.application.dao.AppUser;
import com.pgmanagement.application.enums.ApprovalStatus;

import java.util.List;

public interface IAppUserService {

    public AppUser createAdmin(AppUser request);

    public AppUser RegisterUser(AppUser request);

    public AppUser updateUser(AppUser request, String loginName);

    public AppUser login(String loginname, String password);

    public AppUser getUser(String loginname);

    public List<AppUser> getAll();

    public String getLoginName(long appUserfk);

    public List<AppUser> getAllPendingUsers();
}
