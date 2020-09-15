package com.thoughtworks.service;

import com.thoughtworks.entity.Education;
import com.thoughtworks.exception.UserIdNotMatchException;
import com.thoughtworks.exception.UserNotFoundException;
import com.thoughtworks.repository.EducationRepository;
import com.thoughtworks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EducationService {

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;

    public EducationService(UserRepository userRepository, EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
    }

    public List<Education> getEducationListById(Long id) {
        if (Objects.isNull(userRepository.getUserById(id))) {
            throw new UserNotFoundException();
        }
        return educationRepository.getEducationListById(id);
    }

    public List<Education> addEducation(Long id, Education education) {
        if (Objects.isNull(userRepository.getUserById(id))) {
            throw new UserNotFoundException();
        }
        if (!id.equals(education.getUserId())) {
            throw new UserIdNotMatchException();
        }
        educationRepository.addEducation(id, education);
        return getEducationListById(id);
    }

}
