package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.Notification;
import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final UserService users;  // Inject UserService instead of Map

    @Autowired
    public NotificationService(@Lazy UserService userService) {
        this.users = userService;
    }

    private String generateId() {
        return String.valueOf(idGenerator.getAndIncrement());
    }

    public List<Notification> getNotifications(Long userId, boolean onlyUnread) {
        User user = users.getUserById(userId);  // Fetch the user from UserService
        if (user != null) {
            return onlyUnread
                    ? user.getNotifications().stream().filter(n -> !n.isRead()).collect(Collectors.toList())
                    : user.getNotifications();
        }
        return Collections.emptyList();
    }

    public boolean markNotificationAsRead(Long userId, String notificationId) {
        User user = users.getUserById(userId);
        if (user != null) {
            Optional<Notification> notificationOpt = user.getNotifications().stream()
                    .filter(notification -> notification.getId().equals(notificationId))
                    .findFirst();

            if (notificationOpt.isPresent()) {
                Notification notification = notificationOpt.get();
                if (!notification.isRead()) {
                    notification.markAsRead();
                    return true;
                }
                // Already read notification, you may decide if this is success or failure
                return false;
            }
        }
        return false;  // user not found or notification not found
    }


    public void notifyUser(Long userId, String message) {
        User user = users.getUserById(userId);
        if (user != null) {
            user.addNotification(new Notification(generateId(), message));
        }
    }
}
