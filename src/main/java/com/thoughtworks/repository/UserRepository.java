package com.thoughtworks.repository;

import com.thoughtworks.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepository {

    private static Long NEXT_ID = 1L;
    private final Map<Long, User> userMap = new HashMap<>();

    public User getUserById(Long id) {
        return userMap.get(id);
    }

    public void addUser(User user) {
        Long id = NEXT_ID;
        user.setId(id);
        userMap.put(id, user);
        NEXT_ID++;
    }

    public void clearAll() {
        userMap.clear();
    }
}
