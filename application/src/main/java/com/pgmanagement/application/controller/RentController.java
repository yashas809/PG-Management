package com.pgmanagement.application.controller;

import com.pgmanagement.application.dao.Rent;
import com.pgmanagement.application.dao.RentReceipt;
import com.pgmanagement.application.repository.RentRepository;
import com.pgmanagement.application.service.RentCollectionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/rent")
@CrossOrigin(origins = "*")
public class RentController
{

    @Autowired
    RentCollectionService rentCollectionService;

    @PostMapping("/pay")
    public ResponseEntity payRent(@RequestBody Rent request) {
        try {

                rentCollectionService.collectRent(request);
                return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/view")
    public ResponseEntity ViewRentPaid(@RequestParam(name = "appUserFK") long appUserfk)
    {
        List<Rent> response = rentCollectionService.viewRent(appUserfk);
        if(!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

}
