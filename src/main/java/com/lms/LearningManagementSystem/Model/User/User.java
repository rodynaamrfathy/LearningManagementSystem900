package com.lms.LearningManagementSystem.Model.User;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    
    private List<Notification> notifications = new ArrayList<>();

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

    public boolean equals(String role, String role1) {
        if(role.equals(role1)){return true ;}
        return  false ;
    }
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
     public List<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }
    
}
