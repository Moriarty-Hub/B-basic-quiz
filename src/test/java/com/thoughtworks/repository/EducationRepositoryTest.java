package com.thoughtworks.repository;

import com.thoughtworks.entity.Education;
import com.thoughtworks.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EducationRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_return_education_list_of_user_1() {
        User user = User.builder()
                .name("root")
                .age(28)
                .avatar("www.google.com")
                .description("I am root user")
                .build();
        Education education1 = Education.builder()
                .user(user)
                .year(2005L)
                .title("Primary School")
                .description("I got brilliant performance there")
                .build();
        Education education2 = Education.builder()
                .userId(1L)
                .year(2016L)
                .title("Senior high School")
                .description("I got good grades there")
                .build();

        List<Education> educationList = new LinkedList<>();
        educationList.add(education1);
        educationList.add(education2);

        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(education1);
        entityManager.persistAndFlush(education2);

        List<Education> result = educationRepository.findAll();
        assertEquals(2, result.size());
        assertEquals(educationList, result);
    }
}
