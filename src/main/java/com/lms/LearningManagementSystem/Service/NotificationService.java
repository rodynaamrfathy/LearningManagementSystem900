package com.lms.LearningManagementSystem.Service;
import com.lms.LearningManagementSystem.Model.Notification;
import com.lms.LearningManagementSystem.Model.User;
import org.springframework.stereotype.Service;
import java.util.*;

import java.util.stream.Collectors;

@Service
public class NotificationService implements INotificationService {
    private static Map<String, User> users;

    public NotificationService(Map<String, User> users) {

        this.users = users;
    }

    public List<Notification> getNotifications(String userId, boolean onlyUnread) {
        User user = users.get(userId);
        if (user != null) {
            return onlyUnread
                    ? user.getNotifications().stream().filter(n -> !n.isRead()).collect(Collectors.toList())
                    : user.getNotifications();
        }
        return Collections.emptyList();
    }

    public void markNotificationAsRead(String userId, String notificationId) {
        User user = users.get(userId);
        if (user != null) {
            user.getNotifications().stream()
                    .filter(notification -> notification.getId().equals(notificationId))
                    .findFirst()
                    .ifPresent(Notification::markAsRead);
        }
    }

    public void notifyUser(Long userId, String message) {
        User user = users.get(userId);
        if (user != null) {
            user.addNotification(new Notification(UUID.randomUUID().toString(), message));
        }
    }

}
