package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.User.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, User> userStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1); // Atomic for synchronization

    // Retrieve all users
    public List<User> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    // Retrieve a user by ID
    public User getUserById(Long id) {
        return userStore.get(id);
    }

    // Add a new user
    public User addUser(User user) {
        // Validate and set role
        if (user.getRole() == null || user.getRole().isEmpty()) {
            throw new IllegalArgumentException("User role is required");
        }

        User newUser;
        switch (user.getRole().toLowerCase()) {
            case "admin":
                newUser = new Admin();
                break;
            case "instructor":
                newUser = new Instructor();
                break;
            case "student":
                newUser = new Student();
                break;
            default:
                throw new IllegalArgumentException("Invalid role. Valid roles are Admin, Instructor, and Student.");
        }

        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        long id = idGenerator.getAndIncrement();
        newUser.setId(id);
        userStore.put(id, newUser);
        return newUser;
    }

    // Update an existing user
    public User updateUser(User updateUser, Long id) {
        if (userStore.containsKey(id)) {
            updateUser.setId(id);
            userStore.put(id, updateUser);
            return updateUser;
        }
        return null;
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        userStore.remove(id);
    }
}