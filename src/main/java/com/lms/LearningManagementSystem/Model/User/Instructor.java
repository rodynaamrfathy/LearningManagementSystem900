package com.lms.LearningManagementSystem.Model.User;

import com.lms.LearningManagementSystem.Model.Assessment.Quiz;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {

    private List<Quiz> quizzes = new ArrayList<>();

    public Instructor() {
        this.setRole("Instructor");
    }

    public void createCourse(String courseName) {
        System.out.println("Created course: " + courseName);
    }

    public void addContent(String content) {
        System.out.println("Added content: " + content);
    }

    public void gradeAssignments() {
        System.out.println("Instructor grading assignments.");
    }

    public void removeStudentFromCourse(String studentName) {
        System.out.println("Removed student: " + studentName + " from the course.");
    }

    public void addQuiz(Quiz quiz) {
        this.quizzes.add(quiz);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

}