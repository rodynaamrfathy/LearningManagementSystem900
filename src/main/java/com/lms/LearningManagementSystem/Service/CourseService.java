package com.lms.LearningManagementSystem.Service;
import com.lms.LearningManagementSystem.Model.User.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.Lesson;
import com.lms.LearningManagementSystem.Model.User.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CourseService implements ICourseService{
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final List<Course> courses = new ArrayList<>();
    private final NotificationService notificationService;

    @Autowired
    public CourseService(@Lazy NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    private String generateId() {
        return String.valueOf(idGenerator.getAndIncrement());
    }

    public Course createCourse(String title, String description, int duration) {
        String courseId = generateId();
        Course course = new Course(courseId, title, description, duration);
        courses.add(course);
        return course;
    }
    public  Course findCourseById(String courseId) {
        return courses.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    public boolean addMediaFile(String courseId, String mediaFile) {
        Course course = findCourseById(courseId);
        if (course != null) {
            course.getMediaFiles().add(mediaFile);
            return true;
        }
        return false;
    }
    public Lesson addLesson(String courseId, String title, String content) {
        Course course = findCourseById(courseId);
        if (course != null) {
            String lessonId =  generateId();
            Lesson lesson = new Lesson(lessonId, title, content);
            course.getLessons().add(lesson);
            return lesson;
        }
        return null;
    }
    // Enroll a student in a course

    public String generateOtp(String courseId, String lessonId) {
        Course course = findCourseById(courseId);
        if (course != null) {
            for (Lesson lesson : course.getLessons()) {
                if (lesson.getId().equals(lessonId)) {
                    String otp = UUID.randomUUID().toString().substring(0, 6); // Generate 6-character OTP
                    lesson.setOtp(otp);
                    return otp;
                }
            }
        }
        return null;
    }

    // Mark attendance for a student in a lesson
    public boolean markAttendance(String courseId, String lessonId, String studentId, boolean present) {
        Course course = findCourseById(courseId);
        if (course != null && course.getEnrolledStudents().contains(studentId)) {
            for (Lesson lesson : course.getLessons()) {
                if (lesson.getId().equals(lessonId)) {
                    lesson.markAttendance(studentId, present);
                    return true;
                }
            }
        }
        return false;
    }
    // View attendance for a specific lesson
    public Map<String, Boolean> getLessonAttendance(String courseId, String lessonId) {
        Course course = findCourseById(courseId);
        if (course != null) {
            for (Lesson lesson : course.getLessons()) {
                if (lesson.getId().equals(lessonId)) {
                    return lesson.getAttendance();
                }
            }
        }
        return null;
    }
    // View all courses
    public List<Course> getAllCourses() {
        return courses;
    }

    // View enrolled students
    public List<Long> getEnrolledStudents(String courseId) {
        Course course = findCourseById(courseId);
        return course != null ? course.getEnrolledStudents() : null;
    }

    public Course updateCourse(String courseId, String title, String description, int duration) {
        Course course = findCourseById(courseId);
        if (course != null) {
            // Update course details
            course.setTitle(title);
            course.setDescription(description);
            course.setDuration(duration);

            // Notify all enrolled students about the update
            for (Long studentId : course.getEnrolledStudents()) {
                notificationService.notifyUser(studentId,
                        "The course " + course.getTitle() + " has been updated. Please check for new details.");
            }
            return course;
        }
        return null;
    }
    public boolean deleteCourse(String courseId) {
        Course course = findCourseById(courseId);
        if (course != null) {
            // Notify all enrolled students about course deletion
            for (Long studentId : course.getEnrolledStudents()) {
                notificationService.notifyUser(studentId,
                        "The course " + course.getTitle() + " has been deleted.");
            }

            // Notify the instructor if assigned
            if (course.getInstructor() != null) {
                notificationService.notifyUser(course.getInstructor().getId(),
                        "The course " + course.getTitle() + " you were assigned to teach has been deleted.");
            }

            // Remove the course from the list
            courses.remove(course);
            return true; // Indicate successful deletion
        }
        return false; // Course not found
    }

}


