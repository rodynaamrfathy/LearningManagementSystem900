package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.UserService.AdminService;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import com.lms.LearningManagementSystem.Service.UserService.StudentService;
import com.lms.LearningManagementSystem.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService; // Use the interface instead of the implementation

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
    public ResponseEntity<List<User>> listUsers(
            @RequestParam(required = false) String role) {

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

    // Assign instructor to a course
    @PostMapping("/{instructorId}/assign/{courseId}")
    public boolean assignInstructorToCourse(@PathVariable Long instructorId, @PathVariable String courseId) {
        return InstructorService.assignInstructorToCourse(instructorId, courseId);
    }

    // Instructor generates OTP for a lesson
    @PostMapping("/generate-otp/{instructorId}/{courseId}/{lessonId}")
    public String generateOtpForLesson(@PathVariable Long instructorId, @PathVariable String courseId, @PathVariable String lessonId) {
        return InstructorService.generateOtpForLesson(instructorId, courseId, lessonId);
    }

    // Student enters OTP to mark attendance
    @PostMapping("/mark-attendance/{studentId}/{courseId}/{lessonId}")
    public boolean markAttendance(@PathVariable Long studentId, @PathVariable String courseId, @PathVariable String lessonId,
                                  @RequestParam String otp) {
        return InstructorService.markAttendance(studentId, courseId, lessonId, otp);
    }

}
