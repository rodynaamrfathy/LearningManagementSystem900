package com.lms.LearningManagementSystem.Model.User;

public abstract class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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

    // Abstract methods to enforce implementation in subclasses
    public abstract void specificFunctionality();
}
