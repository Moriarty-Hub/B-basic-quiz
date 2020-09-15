package com.thoughtworks.controller;

import com.thoughtworks.entity.Education;
import com.thoughtworks.entity.User;
import com.thoughtworks.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.thoughtworks.service.UserService;

import java.util.List;

@RestController
@CrossOrigin
public class IntegrationController {

    private final UserService userService;
    private final EducationService educationService;

    public IntegrationController(UserService userService, EducationService educationService) {
        this.userService = userService;
        this.educationService = educationService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}/educations")
    public ResponseEntity<List<Education>> getEducationListById(@PathVariable Long id) {
        return ResponseEntity.ok(educationService.getEducationListById(id));
    }

    @PostMapping("/users/{id}/educations")
    public ResponseEntity<List<Education>> addEducation(@PathVariable Long id, @RequestBody Education education) {
        return new ResponseEntity<>(educationService.addEducation(id, education), HttpStatus.CREATED);
    }
}
