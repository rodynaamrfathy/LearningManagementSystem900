package com.lms.LearningManagementSystem.Model;

public class Student extends User {

    public Student() {
        this.setRole("Student");
    }

    @Override
    public void specificFunctionality() {
        System.out.println("Student-specific functionality.");
    }

    public void enrollInCourse(String courseName) {
        System.out.println("Enrolled in course: " + courseName);
    }

    public void viewCourseMaterials() {
        System.out.println("Viewing course materials.");
    }

    public void submitAssignment(String assignmentName) {
        System.out.println("Submitted assignment: " + assignmentName);
    }
}
