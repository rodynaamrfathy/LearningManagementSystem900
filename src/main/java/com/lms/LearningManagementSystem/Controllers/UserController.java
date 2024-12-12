package com.lms.LearningManagementSystem.Controllers;

import com.lms.LearningManagementSystem.Models.User;
import com.lms.LearningManagementSystem.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userServices.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userServices.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userServices.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User updateUser, @PathVariable Long id) {
        return userServices.updateUser(updateUser, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userServices.deleteUser(id);
    }
}
