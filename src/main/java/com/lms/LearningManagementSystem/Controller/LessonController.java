package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final InstructorService instructorService;

    @Autowired
    public LessonController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    // Instructor generates OTP for a lesson
    @PostMapping("/generate-otp/{instructorId}/{courseId}/{lessonId}")
    public String generateOtpForLesson(@PathVariable Long instructorId, @PathVariable String courseId, @PathVariable String lessonId) {
        return instructorService.generateOtpForLesson(instructorId, courseId, lessonId);
    }

    // Student enters OTP to mark attendance
    @PostMapping("/mark-attendance/{studentId}/{courseId}/{lessonId}")
    public boolean markAttendance(@PathVariable Long studentId, @PathVariable String courseId, @PathVariable String lessonId,
                                  @RequestParam String otp) {
        return instructorService.markAttendance(studentId, courseId, lessonId, otp);
    }
}
