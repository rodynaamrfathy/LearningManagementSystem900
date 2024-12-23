package com.lms.LearningManagementSystem.Model;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private String id;
    private String message;
    private boolean read;

    public Notification(String id, String message) {
        this.id = id;
        this.message = message;
        this.read = false;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public void markAsRead() {
        this.read = true;
    }
}
