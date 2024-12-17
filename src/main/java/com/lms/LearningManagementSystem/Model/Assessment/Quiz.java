package com.lms.LearningManagementSystem.Models.Assessment;

import java.util.ArrayList;
import java.util.List;
public class Quiz {
    private Long id;
    private String title;

    private List<Question> questions = new ArrayList<>();
    private int totalMarks;

    public Quiz(Long id,String title, int totalMarks,List<Question> selectedQuestions) {
        this.id = id;
        this.questions = selectedQuestions;
        this.title = title;
        this.totalMarks = totalMarks;
    }

    // Add question to quiz
    public void addQuestion(Question question) {
        this.questions.add(question);
    }
     public Quiz() {}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public int getTotalMarks() {
        return totalMarks;
    }
    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }




}
   
