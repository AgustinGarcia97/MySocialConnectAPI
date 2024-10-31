package com.example.socialconnect.controller;


import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class UserController {





    @GetMapping("/getUsers")
    public ArrayList<User> getUser() {
        System.out.println("getUsers");
        return null;
    }

}
