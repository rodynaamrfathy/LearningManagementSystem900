package com.lms.LearningManagementSystem.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Course {
    private String id;
    private String title;
    private String description;
    private int duration; // in hours
    private List<String> mediaFiles;
    private List<Lesson> lessons = new ArrayList<>();
    private List<Long> enrolledStudents;
    private Instructor instructor; // Link to the instructor

    public Course(String id, String title, String description, int duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.mediaFiles = new ArrayList<>();
        this.lessons = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public List<String> getMediaFiles() {
        return mediaFiles;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Long> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setDuration(int duration) {
        this.duration=duration;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setTitle(String title) {
        this.title=title;
    }
}



