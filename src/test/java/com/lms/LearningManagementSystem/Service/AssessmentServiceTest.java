package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.Assessment.Question;
import com.lms.LearningManagementSystem.Model.Assessment.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AssessmentServiceTest {

    private AssessmentService assessmentService;


    @Test
    void testCreateQuizSuccessful() {
        // Given
        String title = "Java Basics Quiz";
        int numberOfQuestions = 2;
        int totalMarks = 20;

        // When
        Quiz quiz = assessmentService.createQuiz(title, numberOfQuestions, totalMarks);

        // Then
        assertNotNull(quiz);
        assertEquals(title, quiz.getTitle());
        assertEquals(totalMarks, quiz.getTotalMarks());
        assertEquals(numberOfQuestions, quiz.getQuestions().size());
        // Verify that correct answers are not included in quiz questions
        quiz.getQuestions().forEach(question ->
                assertNull(question.getCorrectAnswer())
        );
    }

    @Test
    void testCreateQuizWithTooManyQuestions() {
        // Given
        String title = "Invalid Quiz";
        int numberOfQuestions = 10; // More than available questions
        int totalMarks = 20;

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            // When
            assessmentService.createQuiz(title, numberOfQuestions, totalMarks);
        });
    }

    @Test
    void testQuizAddedToQuizzesList() {
        // Given
        String title = "Test Quiz";
        int numberOfQuestions = 1;
        int totalMarks = 10;

        // When
        Quiz quiz = assessmentService.createQuiz(title, numberOfQuestions, totalMarks);

        // Then
        List<Quiz> allQuizzes = assessmentService.GetAllquizzes();
        assertTrue(allQuizzes.contains(quiz));
        assertEquals(1, allQuizzes.size());
    }
}