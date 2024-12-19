package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.Assessment.*;
import java.util.*;

public interface IAssessmentService {
    Quiz createQuiz(String title, int num, int totalMarks);
    void SubmitQuiz(Long quizId, Map<String, String> answers);
    int correctAnswersCount(Long quizId, Long studentId);
    void addQuestions(List<Question> questions);
    List<Question> GetQuestions();
    Quiz findQuizById(Long id);
    List<Quiz> GetAllquizzes();
    Assignment createAssignment(String title, String description);
    void submitAssignment(Long assignmentId, String fileName, Long studID);
    Assignment findAssignmentById(Long id);
    List<Assignment> GetAllAssignments();
    void gradeAssignment(Long studentId, Long assessmentId, int marks, String feedback);
    List<Grading> getGradingsForStudent(Long studentId);
    Map<String, Object> trackStudentPerformance(Long studentId); //for track assignments and quizzes for specific student
    List<Assignment> trackStudentAssignments(Long studentId); //for track assignments for specific student
    List<Grading> trackStudentQuizPerformance(Long studentId);//for track  quizzes for specific student
}
