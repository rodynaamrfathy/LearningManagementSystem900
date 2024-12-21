package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.Course;

public class AdminService extends UserService{
    public AdminService(ICourseService courseService, INotificationService notificationService, AssessmentService assessmentService) {
        super(courseService, notificationService, assessmentService);
    }
    public static Course createCourse(String title, String description, int duration) {
        return courseService.createCourse(title, description, duration);
    }
    public static Course updateCourse(String courseId, String title, String description, int duration) {
        Course course = courseService.findCourseById(courseId);
        return courseService.updateCourse(courseId,title,description,duration);
    }
    public static boolean deleteCourse(String courseId) {
        Course course = courseService.findCourseById(courseId);
        return courseService.deleteCourse(courseId);
    }
}
