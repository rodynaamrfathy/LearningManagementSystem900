package com.lms.LearningManagementSystem.Controller;
import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Service.IAssessmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    @Autowired
    private IAssessmentService assessmentService;// to ensure a single instance of AssessmentService is used across the application.

    @GetMapping("/track/{studentId}")
    public Map<String, Object> trackStudentPerformance(@PathVariable Long studentId) {
        return assessmentService.trackStudentPerformance(studentId);
    }

    @GetMapping("/assignments/{studentId}")
   public List<Assignment> trackStudentAssignments(@PathVariable Long studentId) {
    return assessmentService.trackStudentAssignments(studentId);
}


    @GetMapping("/quiz/{studentId}")
    public List<Quiz> trackStudentQuizPerformance(@PathVariable Long studentId) {
        return assessmentService.trackStudentQuizPerformance(studentId);
    }
}
