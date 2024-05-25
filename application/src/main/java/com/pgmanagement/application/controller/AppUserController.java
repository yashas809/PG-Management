package com.pgmanagement.application.controller;

import com.pgmanagement.application.dao.AppUser;
import com.pgmanagement.application.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/appUsers")
@CrossOrigin(origins = "*")
public class AppUserController {

    private IAppUserService service;



    @Autowired
    public AppUserController(IAppUserService service)
    {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AppUser request)
    {

        AppUser response = service.RegisterUser(request);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).build();
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam(name ="loginName") String loginName, @RequestParam(name = "password") String password)
    {
        AppUser response = service.login(loginName, password);
        if(response!=null && (!response.getRoleName().equals("Deleted")))
        {
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.status(422).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestParam(name = "loginName") String loginName, @RequestBody AppUser request)
    {
        AppUser response = service.updateUser(request,loginName);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.status(422).build();
        }
    }

    @GetMapping("/userData")
    public ResponseEntity getUser(@RequestParam(name = "loginName") String loginName)
    {
        AppUser response = service.getUser(loginName);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).build();
    }


}