package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.UserService;
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

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
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
            @RequestParam(required = false) String role)
    {

        try {
            ArrayList<User> filteredUsers = userService.listUsers(role);
            return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

     @PostMapping("/{userId}/enroll")
    public boolean enrollInCourse(@PathVariable Long userId, @RequestParam String courseId) {
        return userService.enrollInCourse(userId, courseId);
    }

    // Assign instructor to a course
    @PostMapping("/{instructorId}/assign/{courseId}")
    public boolean assignInstructorToCourse(@PathVariable Long instructorId, @PathVariable String courseId) {
        return userService.assignInstructorToCourse(instructorId, courseId);
    }

    // Instructor generates OTP for a lesson
    @PostMapping("/generate-otp/{instructorId}/{courseId}/{lessonId}")
    public String generateOtpForLesson(@PathVariable Long instructorId, @PathVariable String courseId, @PathVariable String lessonId) {
        return userService.generateOtpForLesson(instructorId, courseId, lessonId);
    }

    // Student enters OTP to mark attendance
    @PostMapping("/mark-attendance/{studentId}/{courseId}/{lessonId}")
    public boolean markAttendance(@PathVariable Long studentId, @PathVariable String courseId, @PathVariable String lessonId,
                                  @RequestParam String otp) {
        return userService.markAttendance(studentId, courseId, lessonId, otp);
    }

    // Create a course
    @PostMapping("/courses/create")
    public ResponseEntity<Course> createCourse(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int duration) {
        try {
            Course course = userService.createCourse( title, description, duration);
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    // Update a course
    @PutMapping("/courses/{courseId}/update")
    public ResponseEntity<Course> updateCourse(
            @PathVariable String courseId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int duration) {
        try {
            Course updatedCourse = userService.updateCourse( courseId, title, description, duration);
            return ResponseEntity.ok(updatedCourse);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    // Delete a course
    @DeleteMapping("/courses/{courseId}/delete")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        try {
            boolean isDeleted = userService.deleteCourse(courseId);
            if (isDeleted) {
                return ResponseEntity.ok("Course deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
