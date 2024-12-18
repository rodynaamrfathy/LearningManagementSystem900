package com.lms.LearningManagementSystem.Model.User;

public class Admin extends User {

    public Admin() {
        this.setRole("Admin");
    }

    @Override
    public void specificFunctionality() {
        System.out.println("Admin-specific functionality.");
    }

    public void createUser(User user) {
        System.out.println("Created user: " + user.getName());
    }

    public void manageCourses() {
        System.out.println("Admin managing courses.");
    }

    public void generateReports() {
        System.out.println("Admin generating reports.");
    }
}
