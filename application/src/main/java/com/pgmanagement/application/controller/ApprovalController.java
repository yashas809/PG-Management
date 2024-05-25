package com.pgmanagement.application.controller;

import com.pgmanagement.application.enums.ApprovalStatus;
import com.pgmanagement.application.service.IApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approvals")
@CrossOrigin(origins = "*")
public class ApprovalController {

    @Autowired
    IApprovalService service;

    @PutMapping("/action")
    public ResponseEntity approveUser(@RequestParam(name = "appUserFK") long appUserfk, @RequestParam(name = "status") ApprovalStatus status)
    {
        if(service.actionItem(appUserfk,status))
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(422).build();
    }
}
