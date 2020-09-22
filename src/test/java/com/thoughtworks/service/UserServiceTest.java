package com.thoughtworks.service;

import com.thoughtworks.entity.User;
import com.thoughtworks.exception.UserNotFoundException;
import com.thoughtworks.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
        user = User.builder()
                .id(1L)
                .name("root")
                .age(28)
                .avatar("www.google.com")
                .description("I am root user").build();
    }

    @Test
    public void should_return_user_with_id_1_when_id_equals_to_1() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        User result = userService.getUserById(1L);
        assertEquals(user, result);
        verify(userRepository).findById(1L);
    }

    @Test
    public void should_throw_user_not_found_exception_when_id_not_exist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void should_return_user_with_generated_id_when_add_user() {
        when(userRepository.save(any())).thenReturn(user);
        User userWithoutId = User.builder()
                .name("root")
                .age(28)
                .avatar("www.google.com")
                .description("I am root user").build();
        User result = userService.addUser(userWithoutId);
        assertEquals(user, result);
    }
}
