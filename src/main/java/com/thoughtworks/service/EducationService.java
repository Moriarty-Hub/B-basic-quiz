package com.thoughtworks.service;

import com.thoughtworks.entity.Education;
import com.thoughtworks.repository.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<Education> getEducationListById(Long id) {
        return educationRepository.getEducationListById(id);
    }

    public List<Education> addEducation(Long id, Education education) {
        educationRepository.addEducation(id, education);
        return getEducationListById(id);
    }

}
