package com.lms.LearningManagementSystem.Models.Assessment;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public class Assignment {
    private Long id;
    private String title;
    private String description;
    private int marks;      // Marks given after grading
    private String feedback;
    private List<Map.Entry<Long, String>> submittedFiles = new ArrayList<>(); // File names/paths

    public Assignment(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void submitFile(String fileName,Long stringID) {
        this.submittedFiles.add(new SimpleEntry<>(stringID,fileName));
    }

   public List<Map.Entry<Long, String>> getSubmittedFiles() {
        return submittedFiles;
    }
    public void setSubmittedFiles(List<Map.Entry<Long, String>> submittedFiles) {
        this.submittedFiles = submittedFiles;
    }
    public int getMarks() {
        return marks;
    }
    public void setMarks(int marks) {
        this.marks = marks;
    }
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Assignment() {}




}
