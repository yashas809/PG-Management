package com.pgmanagement.application.controller;

import com.pgmanagement.application.dao.AdvertisementInfo;
import com.pgmanagement.application.dao.AppUser;
import com.pgmanagement.application.dao.Rent;
import com.pgmanagement.application.service.IAdvertisementService;
import com.pgmanagement.application.service.IAppUserService;
import com.pgmanagement.application.service.RentCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/owner")
@RestController
@CrossOrigin(origins = "*")
public class OwnerController {
    @Autowired
    private IAppUserService service;

    @Autowired
    RentCollectionService rentCollectionService;

    @Autowired
    IAdvertisementService advertisementService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody AppUser request) {
        AppUser response = service.createAdmin(request);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestParam(name = "loginName") String loginName, @RequestBody AppUser request) {
        AppUser response = service.updateUser(request, loginName);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity getAll() {
        List<AppUser> response = service.getAll();
        if (response.isEmpty()) {
            return ResponseEntity.status(421).build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pendingApproval")
    public ResponseEntity getPendingApprovals() {
        List<AppUser> response = service.getAllPendingUsers();
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/viewRentdetails")
    public ResponseEntity ViewRentPaid(@RequestParam(name = "appUserFK") long appUserfk) {
        List<Rent> response = rentCollectionService.viewRent(appUserfk);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/View/rents/all")
    public ResponseEntity viewAll() {
        List<Rent> response = rentCollectionService.viewAllRent();
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

    @PutMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam(name = "appUserFK") long appUserfk) {
        AppUser appUser = new AppUser();
        appUser.setRoleName("Deleted");
        appUser = service.updateUser(appUser, service.getLoginName(appUserfk));
        if (appUser != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(422).build();
    }

    @PostMapping("/advertisement/add")
    public ResponseEntity addAdvertisement(@RequestBody AdvertisementInfo request)
    {
       boolean success = advertisementService.add(request);
       if(success)
       {
           return ResponseEntity.ok().build();
       }
       return ResponseEntity.status(422).build();
    }

    @PutMapping("/advertisement/udpate")
    public ResponseEntity updateAdvertisement(@RequestParam(name = "id") long advtId,@RequestBody AdvertisementInfo request)
    {
        boolean success = advertisementService.update(advtId,request);
        if(success)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/advertisement/get/available")
    public ResponseEntity getAvailableAdvertisement()
    {
        List<AdvertisementInfo> response = advertisementService.getAvailableAdvertisement();
        if(!response.isEmpty())
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/advertisement/get/all")
    public ResponseEntity getAllAdvertisement()
    {
        List<AdvertisementInfo> response = advertisementService.getAllAdvertisment();
        if(!response.isEmpty())
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/advertisement/get")
    public ResponseEntity getAllAdvertisement(@RequestParam(name = "id") long id)
    {
        AdvertisementInfo response = advertisementService.getAdvertisment(id);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }
}
