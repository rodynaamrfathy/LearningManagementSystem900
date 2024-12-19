package com.lms.LearningManagementSystem.Model;

import java.util.HashMap;
import java.util.Map;
public class Lesson {
    private String id;
    private String title;
    private String content;
    private String otp;
    private Map<String, Boolean> attendance; // Tracks student attendance
    public Lesson(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.attendance = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Map<String, Boolean> getAttendance() {
        return attendance;
    }

    public void markAttendance(String studentId, boolean present) {
        attendance.put(studentId, present);
    }
}
