package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Service.IAssessmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/Assessments")


public class AssessmentController {
    @Autowired
    private IAssessmentService service;
    // Create Quiz
    @PostMapping("/quiz")
    public Quiz createQuiz(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        int totalMarks = (int) payload.get("totalMarks");
        int num = (int) payload.get("num");
        return service.createQuiz(title,num, totalMarks);
    }

    @PostMapping("/quiz/{quizId}/submit")
    public String submitQuizAnswers(@PathVariable Long quizId, @RequestBody Map<String, Object> payload) {
        Long studentId = ((Number) payload.get("studentId")).longValue();
        Map<String, String> submission = (Map<String, String>) payload.get("answers");
        service.SubmitQuiz(quizId, submission);
        int correctAnswersCount = service.correctAnswersCount(quizId, studentId);
        return "You got " + correctAnswersCount + " correct answers!";
    }

    // Add Question to Quiz
    @PostMapping("/create/questions")
    public String addQuestions( @RequestBody List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            return "No questions provided!";
        }
        service.addQuestions(questions);
        return "Questions added successfully!";
    }
    @GetMapping("/quiz/{quizId}")
    public Quiz getQuizById(@PathVariable Long quizId) {
        return service.findQuizById(quizId);
    }
    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return service.GetQuestions();
    }
    @GetMapping("/quizzes")
    public List<Quiz> GetAllquizzes() {
        return service.GetAllquizzes();
    }

    // Create Assignment
    @PostMapping("/assignment")
    public Assignment createAssignment(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        return service.createAssignment(title, description);
    }

    // Submit Assignment
    @PostMapping("/assignment/{assignmentId}/submit")
        public String submitAssignment(@PathVariable Long assignmentId, @RequestBody Map<String, Object> payload) {
        String fileName = (String) payload.get("fileName");
        Long studID = ((Number) payload.get("StudentID")).longValue();
        service.submitAssignment(assignmentId,fileName,studID);
        return "Assignment submitted successfully!";
        
    }
    @GetMapping("/assignment/{assignmentId}")
    public Assignment getAssignmentById(@PathVariable Long assignmentId) {
        return service.findAssignmentById(assignmentId);
    }

    @GetMapping("/assignments")
    public List<Assignment> GetAllAssignments() {
        return service.GetAllAssignments();
    }


    // Grade Assessment
    @PostMapping("/grade")
    public String gradeAssignment(@RequestBody Map<String, Object> payload) {
        Long studentId = ((Number) payload.get("studentId")).longValue();
        //String type = (String) payload.get("assessmentType") ;
        String marks =  (String)payload.get("marks");
        String feedback = (String) payload.get("feedback");
        service.gradeAssignment(studentId,"Assignment", marks, feedback);
        return "Assignment graded successfully!";
    }

 
}
