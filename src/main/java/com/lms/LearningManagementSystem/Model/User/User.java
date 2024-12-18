package com.lms.LearningManagementSystem.Model.User;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;

    // Common methods for all users
    public void login() {
        System.out.println(this.name + " logged in.");
    }

    public void viewProfile() {
        System.out.println("Viewing profile of: " + this.name);
    }

    public void updateProfile(String newName, String newEmail) {
        this.name = newName;
        this.email = newEmail;
        System.out.println("Profile updated: " + this.name);
    }
}