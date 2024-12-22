package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.Assessment.Grading;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grades")
public class GradingController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping
    public String gradeAssignment(@RequestBody Map<String, Object> payload) {
        Long studentId = ((Number) payload.get("studentId")).longValue();
        String marks = (String) payload.get("marks");
        String feedback = (String) payload.get("feedback");
        instructorService.gradeAssignment(studentId, "Assignment", marks, feedback);
        return "Assignment graded successfully!";
    }

    @GetMapping("/track/{studentId}")
    public List<Grading> trackStudentPerformance(@PathVariable Long studentId) {
        return instructorService.trackStudentPerformance(studentId);
    }

    @GetMapping("/assignments/{studentId}")
    public List<Grading> trackStudentAssignments(@PathVariable Long studentId) {
        return instructorService.trackStudentAssignments(studentId);
    }

    @GetMapping("/quizzes/{studentId}")
    public List<Grading> trackStudentQuizPerformance(@PathVariable Long studentId) {
        return instructorService.trackStudentQuizPerformance(studentId);
    }
}
