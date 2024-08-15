package com.jwt.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.example.services.UserService;
import com.jwt.example.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired 
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        // Fetch users from the service and return them
        return userService.getUsers();
    }
}
