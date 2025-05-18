package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.Notification;
import com.lms.LearningManagementSystem.Service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId, @RequestParam(defaultValue = "false") boolean onlyUnread) {
        return notificationService.getNotifications(userId, onlyUnread);
    }

    @PostMapping("/{userId}/{notificationId}/read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long userId, @PathVariable String notificationId) {
        boolean success = notificationService.markNotificationAsRead(userId, notificationId);
        if (success) {
            return ResponseEntity.ok("Notification marked as read.");
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Notification not found or already read for user ID: " + userId);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

}

