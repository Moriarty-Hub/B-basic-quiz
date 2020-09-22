package com.thoughtworks.service;

import com.thoughtworks.entity.Education;
import com.thoughtworks.entity.User;
import com.thoughtworks.exception.UserIdNotMatchException;
import com.thoughtworks.exception.UserNotFoundException;
import com.thoughtworks.repository.EducationRepository;
import com.thoughtworks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducationService {

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;

    public EducationService(UserRepository userRepository, EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
    }

    public List<Education> getEducationListById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return user.get().getEducationList();
    }

    public List<Education> addEducation(Long id, Education education) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        if (!id.equals(education.getUserId())) {
            throw new UserIdNotMatchException();
        }
        education.setUser(user.get());
        educationRepository.save(education);
        return getEducationListById(id);
    }

}
