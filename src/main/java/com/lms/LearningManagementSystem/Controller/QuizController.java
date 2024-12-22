package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import com.lms.LearningManagementSystem.Service.UserService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Quiz createQuiz(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        int totalMarks = (int) payload.get("totalMarks");
        int num = (int) payload.get("num");
        return instructorService.createQuiz(title, num, totalMarks);
    }

    @PostMapping("/{quizId}/submit")
    public String submitQuizAnswers(@PathVariable Long quizId, @RequestBody Map<String, Object> payload) {
        Long studentId = ((Number) payload.get("studentId")).longValue();
        Map<String, String> submission = (Map<String, String>) payload.get("answers");
        studentService.SubmitQuiz(quizId, submission);
        int correctAnswersCount = instructorService.correctAnswersCount(quizId, studentId);
        return "You got " + correctAnswersCount + " correct answers!";
    }

    // @GetMapping("/{quizId}")
    // public Quiz getQuizById(@PathVariable Long quizId) {
    //     return studentService.findQuizById(quizId);
    // }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return studentService.GetAllquizzes();
    }

    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return instructorService.GetQuestions();
    }

    @PostMapping("/create/questions")
    public String addQuestions(@RequestBody List<Question> questions) {
        instructorService.addQuestions(questions);
        return "Questions added successfully!";
    }
}
