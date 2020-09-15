package com.thoughtworks.controller;

import com.thoughtworks.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.thoughtworks.service.UserService;

@RestController
public class IntegrationController {

    private final UserService userService;

    public IntegrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
