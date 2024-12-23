package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    @GetMapping("/track/{studentId}")
    public ResponseEntity<List<Grading>> trackStudentPerformance(@PathVariable Long studentId) {
        List<Grading> performance = InstructorService.trackStudentPerformance(studentId);
        if (performance == null || performance.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 if no data found
        }
        return new ResponseEntity<>(performance, HttpStatus.OK); // Return 200 with the performance data
    }


    @GetMapping("/assignments/{studentId}")
    public ResponseEntity<List<Grading>> trackStudentAssignments(@PathVariable Long studentId) {
        List<Grading> assignments = InstructorService.trackStudentAssignments(studentId);
        if (assignments == null || assignments.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 if no assignments found
        }
        return new ResponseEntity<>(assignments, HttpStatus.OK); // Return 200 with the assignments data
    }


    @GetMapping("/quiz/{studentId}")
    public ResponseEntity<List<Grading>> trackStudentQuizPerformance(@PathVariable Long studentId) {
        List<Grading> quizPerformance = InstructorService.trackStudentQuizPerformance(studentId);
        if (quizPerformance == null || quizPerformance.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 if no quiz performance found
        }
        return new ResponseEntity<>(quizPerformance, HttpStatus.OK); // Return 200 with the quiz performance data
    }

}
