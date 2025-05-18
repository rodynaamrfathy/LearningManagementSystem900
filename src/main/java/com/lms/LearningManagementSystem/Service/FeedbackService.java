package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.Feedback;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FeedbackService {
    private final List<Feedback> feedbackList = new ArrayList<>();
    private final AtomicLong feedbackIdGenerator = new AtomicLong(1);

    public Feedback submitFeedback(Long studentId, String courseId, String message, int rating) {
        Feedback feedback = new Feedback(feedbackIdGenerator.getAndIncrement(), studentId, courseId, message, rating);
        feedbackList.add(feedback);
        return feedback;
    }

    public List<Feedback> getFeedbackByCourse(String courseId) {
        List<Feedback> result = new ArrayList<>();
        for (Feedback f : feedbackList) {
            if (f.getCourseId().equals(courseId)) {
                result.add(f);
            }
        }
        return result;
    }

    public List<Feedback> getFeedbackByStudent(Long studentId) {
        List<Feedback> result = new ArrayList<>();
        for (Feedback f : feedbackList) {
            if (f.getStudentId().equals(studentId)) {
                result.add(f);
            }
        }
        return result;
    }
}
