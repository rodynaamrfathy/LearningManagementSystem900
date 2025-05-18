package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.Feedback;
import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.FeedbackService;
import com.lms.LearningManagementSystem.Service.UserService.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final CourseService courseService;

    public FeedbackController(FeedbackService feedbackService, CourseService courseService) {
        this.feedbackService = feedbackService;
        this.courseService = courseService;
    }

    @PostMapping("/submit/{studentId}/{courseId}")
    public ResponseEntity<?> submitFeedback(
            @PathVariable Long studentId,
            @PathVariable String courseId,
            @RequestBody Map<String, Object> payload) {

        //  Validate student
        User student = UserService.userStore.get(studentId);
        if (student == null || !"Student".equalsIgnoreCase(student.getRole())) {
            return ResponseEntity.badRequest().body("Invalid student ID or user is not a student.");
        }

        //  Validate course
        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            return ResponseEntity.badRequest().body("Course not found.");
        }

        //  Extract and validate feedback details
        String message = (String) payload.get("message");
        int rating = (int) payload.get("rating");

        if (rating < 1 || rating > 5) {
            return ResponseEntity.badRequest().body("Rating must be between 1 and 5.");
        }

        Feedback feedback = feedbackService.submitFeedback(studentId, courseId, message, rating);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/course/{courseId}")
    public List<Feedback> getCourseFeedback(@PathVariable String courseId) {
        return feedbackService.getFeedbackByCourse(courseId);
    }

    @GetMapping("/student/{studentId}")
    public List<Feedback> getStudentFeedback(@PathVariable Long studentId) {
        return feedbackService.getFeedbackByStudent(studentId);
    }
}
