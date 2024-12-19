package com.lms.LearningManagementSystem.Service;
import com.lms.LearningManagementSystem.Model.User.Instructor;
import com.lms.LearningManagementSystem.Model.Notification;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.Lesson;

import java.util.List;
import java.util.Map;

public interface ICourseService {
    Course createCourse(String title, String description, int duration);
    Course findCourseById(String courseId);

    boolean addMediaFile(String courseId, String mediaFile);

    Lesson addLesson(String courseId, String title, String content);

    String generateOtp(String courseId, String lessonId);

    boolean markAttendance(String courseId, String lessonId, String studentId, boolean present);

    Map<String, Boolean> getLessonAttendance(String courseId, String lessonId);

    List<Course> getAllCourses();

    List<Long> getEnrolledStudents(String courseId);
    Course updateCourse(String courseId, String title, String description, int duration);

    boolean deleteCourse(String courseId);


}