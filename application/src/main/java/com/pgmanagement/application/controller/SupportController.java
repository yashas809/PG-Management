package com.pgmanagement.application.controller;

import com.pgmanagement.application.dao.CommunicationCase;
import com.pgmanagement.application.dao.SupportTicketDAO;
import com.pgmanagement.application.entity.RentEntity;
import com.pgmanagement.application.entity.SupportTicketEntity;
import com.pgmanagement.application.enums.Reason;
import com.pgmanagement.application.repository.AppuserRepository;
import com.pgmanagement.application.repository.RoleRepository;
import com.pgmanagement.application.repository.SupportTicketRepository;
import com.pgmanagement.application.service.IAppUserService;
import common.dao.CaseInfo;
import common.dao.Communication;
import common.service.ICommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatbox")
@CrossOrigin(origins = "*")
public class SupportController {
    @Autowired
    ICommunicationService communicationService;

    @Autowired
    IAppUserService appUserService;

    @Autowired
    AppuserRepository appuserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SupportTicketRepository supportTicketRepository;

    @PostMapping("/createCase")
    public ResponseEntity createCase(@RequestBody CommunicationCase request) {
        long communicationFrom = appUserService.getUser(request.getUserName()).getId();
        long communicationTo = appuserRepository.findByroleFK(roleRepository.findByroleName("Owner").get().getRolepk()).get().get(0).getAppUserPK();
        if (communicationFrom != 0L && communicationTo != 0L) {
            Communication communication = new Communication();
            communication.setFromId(communicationFrom);
            communication.setToId(communicationTo);
            communication.setMessage(request.getMessage());
            communication.setMessageDirection(request.getMessageDirection());
            communication = communicationService.createCase(communication);
            if (communication != null) {
                supportTicketRepository.save(SupportTicketEntity.construct(0L, communication.getCaseId(), request.getReasonCode(), communicationFrom));
                return ResponseEntity.ok(communication);
            }
        }
        return ResponseEntity.status(422).build();
    }

    @PostMapping("/sendMessage")
    public ResponseEntity SendMessage(@RequestParam(value = "caseId") long caseId, @RequestBody Communication request) {
        boolean response = communicationService.SendMessage(caseId, request);
        if (response) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/getConversation")
    public ResponseEntity createMessage(@RequestParam(name = "caseId") long caseId) {
        List<Communication> response = communicationService.getMessages(caseId);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/checkIfAlreadyExists")
    public ResponseEntity checkAlreadyExists(@RequestParam(name = "appUserFK") long appUserFK, @RequestParam(name = "reasonCode") Reason reason) {
        List<SupportTicketEntity> supportTicketEntities = supportTicketRepository.findByappUserFK(appUserFK);
        for (SupportTicketEntity entity : supportTicketEntities) {
            if (entity.getReason().equals(reason)) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(421).build();
    }

    @GetMapping("/getAllCaseID")
    public ResponseEntity getAllCaseId(@RequestParam(name = "appUserFK") long appUserFK) {
        List<SupportTicketDAO> response = new ArrayList<>();
        List<SupportTicketEntity> supportTicketEntity = supportTicketRepository.findByappUserFK(appUserFK);
        for (SupportTicketEntity entity : supportTicketEntity) {
            response.add(SupportTicketDAO.build(entity.getCaseIDFK(),
                    entity.getReason().name() + "-" + entity.getCaseIDFK() + "-" + entity.getTicketID()));
        }
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/owner/getAllCases")
    public ResponseEntity getAllCases()
    {
        List<SupportTicketDAO> response = new ArrayList<>();
       List<CaseInfo> caseInfos = communicationService.getCaseId(appuserRepository.findByroleFK(roleRepository.findByroleName("Owner").get().getRolepk()).get().get(0).getAppUserPK());
       for(CaseInfo caseInfo: caseInfos)
       {
           SupportTicketEntity entity =  supportTicketRepository.findBycaseIDFK(caseInfo.getCaseId());
           response.add(SupportTicketDAO.build(caseInfo.getCaseId(),entity.getReason().name() + "-" + entity.getCaseIDFK() + "-" + entity.getTicketID()));
       }
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

}
