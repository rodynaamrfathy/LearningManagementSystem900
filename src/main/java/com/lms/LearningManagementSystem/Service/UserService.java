package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User addUser(User user);
    User updateUser(User updateUser, Long id);
    void deleteUser(Long id);
}