package com.lms.LearningManagementSystem.Service.UserService;

import com.lms.LearningManagementSystem.Model.Assessment.Assignment;
import com.lms.LearningManagementSystem.Model.Assessment.Quiz;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.AssessmentService;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService extends UserService {
    public StudentService(CourseService courseService, NotificationService notificationService, AssessmentService assessmentService) {
        super(courseService, notificationService, assessmentService);
    }

    public static boolean enrollInCourse(Long userId, String courseId) {
        User user = userStore.get(userId);
        if (user == null) {
            return false; // User doesn't exist
        }
        Course course = courseService.findCourseById(courseId);
        if (course != null && !course.getEnrolledStudents().contains(userId)) {
            // Add student to course
            course.getEnrolledStudents().add(Long.parseLong(String.valueOf(userId)));
            // Notify student
            notificationService.notifyUser(userId,
                    "You have been enrolled in the course: " + course.getTitle());
            // Notify instructor
            if (course.getInstructor() != null) {
                notificationService.notifyUser(course.getInstructor().getId(),
                        "A new student has enrolled in your course: " + course.getTitle());
            }
            return true;
        }
        return false;
    }

    public static void SubmitQuiz(Long quizId, Map<String, String> answers) {
        assessmentService.SubmitQuiz(quizId, answers);
    }

    public static void submitAssignment(Long assignmentId, String fileName, Long studID) {
        assessmentService.submitAssignment(assignmentId, fileName, studID);
    }

    // Get Assignment by ID
    public static Assignment findAssignmentById(Long id) {
        return assessmentService.findAssignmentById(id);
    }

    public static List<Assignment> GetAllAssignments() {
        return assessmentService.GetAllAssignments();
    }


    // Get Quiz by ID
    public static Quiz findQuizById(Long id) {
        return assessmentService.findQuizById(id);
    }

    public static List<Quiz> GetAllquizzes() {
        return assessmentService.GetAllquizzes();
    }

}
