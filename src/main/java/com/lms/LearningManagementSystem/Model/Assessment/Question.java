package com.lms.LearningManagementSystem.Models.Assessment;
import java.util.List;
public class Question {
    private Long id;
    private String text;
    //private String type; // MCQ, True/False, Short Answer
    private List<String> options; // For MCQ
    private String correctAnswer;


    public Question(Long id, String text, List<String> options, String correctAnswer) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
    public Question() {}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


}
