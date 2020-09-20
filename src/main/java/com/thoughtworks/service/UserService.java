package com.thoughtworks.service;

import com.thoughtworks.entity.User;
import com.thoughtworks.exception.UserNotFoundException;
import com.thoughtworks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return user.get();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
}
