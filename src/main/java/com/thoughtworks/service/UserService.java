package com.thoughtworks.service;

import com.thoughtworks.entity.User;
import org.springframework.stereotype.Service;
import com.thoughtworks.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public User addUser(User user) {
        return userRepository.addUser(user);
    }
}
