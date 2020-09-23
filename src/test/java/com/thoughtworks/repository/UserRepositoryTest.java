package com.thoughtworks.repository;

import com.thoughtworks.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_return_user_which_id_equals_to_1() {
        entityManager.persistAndFlush(User.builder()
                .name("root")
                .age(28)
                .avatar("www.google.com")
                .description("I am root user")
                .build());

        Optional<User> optionalUser = userRepository.findById(1L);

        assertTrue(optionalUser.isPresent());
        assertEquals(optionalUser.get(), User.builder()
                .id(1L)
                .name("root")
                .age(28)
                .avatar("www.google.com")
                .description("I am root user")
                .build());
    }
}
