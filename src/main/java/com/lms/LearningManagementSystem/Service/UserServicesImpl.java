package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserServicesImpl implements UserServices {

    private final Map<Long, User> userStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1); // Atomic for synchronization

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    @Override
    public User getUserById(Long id) {
        return userStore.get(id);
    }

    @Override
    public User addUser(User user) {
        long id = idGenerator.getAndIncrement();
        user.setId(id);
        userStore.put(id, user);
        return user;
    }

    @Override
    public User updateUser(User updateUser, Long id) {
        if (userStore.containsKey(id)) {
            updateUser.setId(id);
            userStore.put(id, updateUser);
            return updateUser;
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userStore.remove(id);
    }
}
