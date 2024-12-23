package com.lms.LearningManagementSystem;

import com.lms.LearningManagementSystem.Model.Assessment.Grading;
import com.lms.LearningManagementSystem.Model.Assessment.Quiz;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.User.Instructor;
import com.lms.LearningManagementSystem.Model.User.Student;
import com.lms.LearningManagementSystem.Service.AssessmentService;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.NotificationService;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import com.lms.LearningManagementSystem.Service.UserService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServiceTest {

    private CourseService courseService;
    private NotificationService notificationService;
    private AssessmentService assessmentService;
    private InstructorService instructorService;

    @BeforeEach
    void setUp() {
        courseService = mock(CourseService.class);
        notificationService = mock(NotificationService.class);
        assessmentService = mock(AssessmentService.class);
        instructorService = new InstructorService(courseService, notificationService, assessmentService);
    }

    @Test
    void testAssignInstructorToCourse_Success() {
        Long instructorId = 1L;
        String courseId = "course1";
        Instructor instructor = mock(Instructor.class);
        Course course = mock(Course.class);

        when(courseService.findCourseById(courseId)).thenReturn(course);
        UserService.userStore.put(instructorId, instructor);

        boolean result = InstructorService.assignInstructorToCourse(instructorId, courseId);

        assertTrue(result);
        verify(course).setInstructor(instructor);
        verify(notificationService).notifyUser(eq(instructorId), anyString());
    }

    @Test
    void testAssignInstructorToCourse_Failure_UserNotFound() {
        Long instructorId = 2L;
        String courseId = "course2";

        boolean result = InstructorService.assignInstructorToCourse(instructorId, courseId);

        assertFalse(result);
        verify(courseService, never()).findCourseById(anyString());
        verify(notificationService, never()).notifyUser(anyLong(), anyString());
    }

    @Test
    void testGenerateOtpForLesson_Success() {
        Long instructorId = 1L;
        String courseId = "course1";
        String lessonId = "lesson1";
        Instructor instructor = mock(Instructor.class);

        UserService.userStore.put(instructorId, instructor);
        when(courseService.generateOtp(courseId, lessonId)).thenReturn("123456");

        String otp = InstructorService.generateOtpForLesson(instructorId, courseId, lessonId);

        assertNotNull(otp);
        assertEquals("123456", otp);
    }

    @Test
    void testMarkAttendance_Success() {
        Long studentId = 1L;
        String courseId = "course1";
        String lessonId = "lesson1";
        String otp = "123456";
        Student student = mock(Student.class);
        UserService.userStore.put(studentId, student);
        when(courseService.markAttendance(courseId, lessonId, studentId.toString(), true)).thenReturn(true);

        boolean result = InstructorService.markAttendance(studentId, courseId, lessonId, otp);

        assertTrue(result);
    }

    @Test
    void testCreateQuiz() {
        String title = "Quiz1";
        int num = 10;
        int totalMarks = 100;
        Quiz quiz = mock(Quiz.class);

        when(assessmentService.createQuiz(title, num, totalMarks)).thenReturn(quiz);

        Quiz createdQuiz = InstructorService.createQuiz(title, num, totalMarks);

        assertNotNull(createdQuiz);
    }

    @Test
    void testTrackStudentPerformance() {
        Long studentId = 1L;
        List<Grading> gradings = Collections.emptyList();

        when(assessmentService.trackStudentPerformance(studentId)).thenReturn(gradings);

        List<Grading> result = InstructorService.trackStudentPerformance(studentId);

        assertEquals(gradings, result);
    }
}
