package com.lms.LearningManagementSystem.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Feedback {
    private Long id;
    private Long studentId;
    private String courseId;
    private String message;
    private int rating; // 1 to 5

    public Feedback(Long id, Long studentId, String courseId, String message, int rating) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.message = message;
        this.rating = rating;
    }
}
