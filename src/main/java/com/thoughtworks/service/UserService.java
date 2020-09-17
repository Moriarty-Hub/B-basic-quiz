package com.thoughtworks.service;

import com.thoughtworks.entity.User;
import com.thoughtworks.exception.UserNotFoundException;
import com.thoughtworks.repository.EducationRepository;
import org.springframework.stereotype.Service;
import com.thoughtworks.repository.UserRepository;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;

    public UserService(UserRepository userRepository, EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
    }

    public User getUserById(Long id) {
        User user =  userRepository.getUserById(id);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public User addUser(User user) {
        User createdUser = userRepository.addUser(user);
        // GTB: 这个处理可以放到 Repository 中处理，返回时直接返回空 list 即可
        educationRepository.initializeEducationListForNewUser(createdUser.getId());
        return createdUser;
    }
}
