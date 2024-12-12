package com.lms.LearningManagementSystem.Services;

import com.lms.LearningManagementSystem.Models.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserServices {

    private final Map<Long, User> userStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<User> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    public User getUserById(Long id) {
        return userStore.get(id);
    }

    public User addUser(User user) {
        long id = idGenerator.getAndIncrement();
        user.setId(id);
        userStore.put(id, user);
        return user;
    }

    public User updateUser(User updateUser, Long id) {
        if (userStore.containsKey(id)) {
            updateUser.setId(id);
            userStore.put(id, updateUser);
            return updateUser;
        }
        return null;
    }

    public void deleteUser(Long id) {
        userStore.remove(id);
    }
}
