package com.lms.LearningManagementSystem.Controller;
import java.util.List;
import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Service.AssessmentService;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import com.lms.LearningManagementSystem.Service.UserService.StudentService;
import com.lms.LearningManagementSystem.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/Assessments")
public class AssessmentController {

    // Create Quiz
    @PostMapping("/quiz")
    public Quiz createQuiz(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        int totalMarks = (int) payload.get("totalMarks");
        int num = (int) payload.get("num");
        return InstructorService.createQuiz(title,num, totalMarks);
    }

    @PostMapping("/quiz/{quizId}/submit")
    public ResponseEntity<String> submitQuizAnswers(@PathVariable Long quizId, @RequestBody Map<String, Object> payload) {
        // Check if the quiz exists
        Quiz quiz = StudentService.findQuizById(quizId);
        if (quiz == null) {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND); // Return 404 if quiz not found
        }

        Long studentId = ((Number) payload.get("studentId")).longValue();
        Map<String, String> submission = (Map<String, String>) payload.get("answers");

        // Process submission
        StudentService.SubmitQuiz(quizId, submission);
        int correctAnswersCount = InstructorService.correctAnswersCount(quizId, studentId);

        return new ResponseEntity<>("You got " + correctAnswersCount + " correct answers!", HttpStatus.OK);
    }


    // Add Question to Quiz
    @PostMapping("/create/questions")
    public String addQuestions( @RequestBody List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            return "No questions provided!";
        }
        InstructorService.addQuestions(questions);
        return "Questions added successfully!";
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = StudentService.findQuizById(id);
        if (quiz == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 if no quiz is found
        }
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }
    
    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return InstructorService.GetQuestions();
    }
    @GetMapping("/quizzes")
    public List<Quiz> GetAllquizzes() {
        return StudentService.GetAllquizzes();
    }

    // Create Assignment
    @PostMapping("/assignment")
    public Assignment createAssignment(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        return InstructorService.createAssignment(title, description);
    }

    // Submit Assignment
    @PostMapping("/assignment/{assignmentId}/submit")
    public ResponseEntity<String> submitAssignment(@PathVariable Long assignmentId, @RequestBody Map<String, Object> payload) {
        // Check if the assignment exists
        Assignment assignment = StudentService.findAssignmentById(assignmentId);
        if (assignment == null) {
            return new ResponseEntity<>("Assignment not found", HttpStatus.NOT_FOUND); // Return 404 if assignment not found
        }

        String fileName = (String) payload.get("fileName");
        Long studID = ((Number) payload.get("StudentID")).longValue();

        // Submit the assignment
        StudentService.submitAssignment(assignmentId, fileName, studID);
        return new ResponseEntity<>("Assignment submitted successfully!", HttpStatus.OK);
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long assignmentId) {
        Assignment assignment = StudentService.findAssignmentById(assignmentId);
        if (assignment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 if assignment not found
        }
        return new ResponseEntity<>(assignment, HttpStatus.OK);
    }


    @GetMapping("/assignments")
    public List<Assignment> GetAllAssignments() {
        return StudentService.GetAllAssignments();
    }


    // Grade Assessment
    @PostMapping("/grade")
    public String gradeAssignment(@RequestBody Map<String, Object> payload) {
        Long studentId = ((Number) payload.get("studentId")).longValue();
        //String type = (String) payload.get("assessmentType") ;
        String marks =  (String)payload.get("marks");
        String feedback = (String) payload.get("feedback");
        InstructorService.gradeAssignment(studentId,"Assignment", marks, feedback);
        return "Assignment graded successfully!";
    }

 
}
