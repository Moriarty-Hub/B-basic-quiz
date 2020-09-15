package com.thoughtworks.repository;

import com.thoughtworks.entity.Education;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class EducationRepository {

    private final Map<Long, List<Education>> educationMap = new HashMap<>();

    public void initializeEducationListForNewUser(Long userId) {
        educationMap.put(userId, new LinkedList<>());
    }

    public void addEducation(Long id, Education education) {
        educationMap.get(id).add(education);
    }

    public List<Education> getEducationListById(Long id) {
        return educationMap.get(id);
    }
}
