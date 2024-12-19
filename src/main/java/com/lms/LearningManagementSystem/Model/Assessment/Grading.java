package com.lms.LearningManagementSystem.Model.Assessment;

public class Grading {
    private Long id;
    private Long studentId;
    private String type;   // "Quiz" or "Assignment"
    private String marks;
    private String feedback;

    public Grading(Long id, Long studentId, String type, String marks, String feedback) {
        this.id = id;
        this.studentId = studentId;
        this.type = type;
        this.marks = marks;
        this.feedback = feedback;
    }

    public Grading(Long id, Long studentId, String marks) {
        this.id = id;
        this.studentId = studentId;
        this.marks = marks;
    }

    public Grading() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

        public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;

    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


}
