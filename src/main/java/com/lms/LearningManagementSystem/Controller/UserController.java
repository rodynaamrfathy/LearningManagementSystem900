package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.UserService.UserService;
import com.lms.LearningManagementSystem.Service.UserService.StudentService;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User createdUser = userService.addUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User updateUser, @PathVariable Long id) {
        return userService.updateUser(updateUser, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsers(@RequestParam(required = false) String role) {
        try {
            ArrayList<User> filteredUsers = userService.listUsers(role);
            return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}/enroll")
    public boolean enrollInCourse(@PathVariable Long userId, @RequestParam String courseId) {
        return StudentService.enrollInCourse(userId, courseId);
    }

    @PostMapping("/{instructorId}/assign/{courseId}")
    public boolean assignInstructorToCourse(@PathVariable Long instructorId, @PathVariable String courseId) {
        return InstructorService.assignInstructorToCourse(instructorId, courseId);
    }
}
