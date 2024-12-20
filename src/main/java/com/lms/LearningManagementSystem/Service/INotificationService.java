package com.lms.LearningManagementSystem.Service;


import com.lms.LearningManagementSystem.Model.Notification;

import java.util.List;

public interface INotificationService {
    List<Notification> getNotifications(Long userId, boolean onlyUnread);

    void markNotificationAsRead(Long userId, String notificationId);

    void notifyUser(Long userId, String message);
}