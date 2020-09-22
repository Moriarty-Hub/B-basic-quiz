package com.thoughtworks.service;

import com.thoughtworks.entity.Education;
import com.thoughtworks.entity.User;
import com.thoughtworks.exception.UserNotFoundException;
import com.thoughtworks.repository.EducationRepository;
import com.thoughtworks.repository.UserRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EducationServiceTest {

    private EducationService educationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EducationRepository educationRepository;

    private User user;
    private Education education;

    @BeforeEach
    public void setUp() {
        educationService = new EducationService(userRepository, educationRepository);
        user = User.builder()
                .id(1L)
                .name("root")
                .age(20)
                .avatar("https://www.google.com/")
                .description("This is a user for test")
                .build();
        education = Education.builder()
                .user(user)
                .year(2005L)
                .title("Primary School")
                .description("I got brilliant performance there")
                .build();
        user.setEducationList(new LinkedList<>());
        user.getEducationList().add(education);
    }

    @Test
    public void should_get_education_list_belongs_to_user_with_id_1() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        List<Education> result = educationService.getEducationListById(1L);
        assertEquals(Collections.singletonList(education), result);
    }

    @Test
    public void should_throw_user_not_found_exception_when_user_id_not_exist() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> educationService.getEducationListById(1L));
    }

    @Test
    @Ignore
    public void should_get_the_latest_education_list_when_add_new_education() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        Education insertedEducation = Education.builder()
                .userId(1L)
                .year(2016L)
                .title("Senior high School")
                .description("I got good grades there")
                .build();
        List<Education> returnedEducationList = new LinkedList<>(user.getEducationList());
        returnedEducationList.add(insertedEducation);
        when(educationService.getEducationListById(1L)).thenReturn(returnedEducationList);

        List<Education> result = educationService.addEducation(1L, insertedEducation);

        assertEquals(returnedEducationList, result);

        verify(educationRepository).save(insertedEducation);
    }
}
