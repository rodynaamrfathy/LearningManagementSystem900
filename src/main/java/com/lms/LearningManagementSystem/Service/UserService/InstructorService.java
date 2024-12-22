package com.lms.LearningManagementSystem.Service.UserService;

import com.lms.LearningManagementSystem.Model.Assessment.Assignment;
import com.lms.LearningManagementSystem.Model.Assessment.Grading;
import com.lms.LearningManagementSystem.Model.Assessment.Question;
import com.lms.LearningManagementSystem.Model.Assessment.Quiz;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.User.Instructor;
import com.lms.LearningManagementSystem.Model.User.Student;
import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.AssessmentService;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService extends UserService {

    public InstructorService(CourseService courseService, NotificationService notificationService, AssessmentService assessmentService) {
        super(courseService, notificationService, assessmentService);
    }

    public static boolean assignInstructorToCourse(Long instructorId, String courseId) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            return false; // User is either not found or not an instructor
        }

        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            return false; // Course not found
        }

        // Assign the instructor to the course
        course.setInstructor((Instructor) user);


        // Notify the instructor about the assignment
        notificationService.notifyUser(instructorId,
                "You have been assigned to teach the course: " + course.getTitle());

        // Notify students about the new instructor
        for (Long studentId : course.getEnrolledStudents()) {
            notificationService.notifyUser(studentId,
                    "The course " + course.getTitle() + " now has a new instructor: " + user.getName());
        }

        return true;
    }

    public static String generateOtpForLesson(Long instructorId, String courseId, String lessonId) {
        User user = userStore.get(instructorId);
        if (user instanceof Instructor) {
            return courseService.generateOtp(courseId, lessonId);
        }
        return null; // Only instructors can generate OTPs
    }

    public static boolean markAttendance(Long studentId, String courseId, String lessonId, String otp) {
        User user = userStore.get(studentId);
        if (user instanceof Student) {
            return courseService.markAttendance(courseId, lessonId, String.valueOf(studentId), true);
        }
        return false;
    }

    public static Quiz createQuiz(String title, int num, int totalMarks) {
        return assessmentService.createQuiz(title, num, totalMarks);
    }

    public static int correctAnswersCount(Long quizId, Long studentId) {
        return assessmentService.correctAnswersCount(quizId, studentId);
    }

    public static void addQuestions(List<Question> questions) {
        assessmentService.addQuestions(questions); }

     public static List<Question> GetQuestions() {

        return assessmentService.GetQuestions();
    }

    public static Assignment createAssignment(String title, String description) {
        return assessmentService.createAssignment(title, description);
    }
    public static void gradeAssignment(Long studentId, String type, String marks, String feedback) {
        assessmentService.gradeAssignment(studentId, type, marks, feedback);
    }

    // Get Gradings
    public static List<Grading> trackStudentPerformance(Long studentId) {
        return assessmentService.trackStudentPerformance(studentId);
    }

    public static List<Grading> trackStudentAssignments(Long studentId) {
        return assessmentService.trackStudentAssignments(studentId);
    }

    public static List<Grading> trackStudentQuizPerformance(Long studentId) {
        return assessmentService.trackStudentQuizPerformance(studentId);
    }

}