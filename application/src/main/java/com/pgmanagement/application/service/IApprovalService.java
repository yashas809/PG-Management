package com.pgmanagement.application.service;

import com.pgmanagement.application.enums.ApprovalStatus;

public interface IApprovalService
{
    public void SendForApproval(long appUserFK);

    public boolean actionItem(long appUserFK, ApprovalStatus status);
}
