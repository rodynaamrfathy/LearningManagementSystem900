package com.lms.LearningManagementSystem.Controller;
import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.UserService.AdminService;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import com.lms.LearningManagementSystem.Service.UserService.StudentService;
import com.lms.LearningManagementSystem.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService; // Use the interface instead of the implementation

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.addUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User updateUser, @PathVariable Long id) {
        return userService.updateUser(updateUser, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsers(
            @RequestParam(required = false) String role)
    {

        try {
            ArrayList<User> filteredUsers = userService.listUsers(role);
            return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

     @PostMapping("/{userId}/enroll")
    public boolean enrollInCourse(@PathVariable Long userId, @RequestParam String courseId) {
        return StudentService.enrollInCourse(userId, courseId);
    }

    // Assign instructor to a course
    @PostMapping("/{instructorId}/assign/{courseId}")
    public boolean assignInstructorToCourse(@PathVariable Long instructorId, @PathVariable String courseId) {
        return InstructorService.assignInstructorToCourse(instructorId, courseId);
    }

    // Instructor generates OTP for a lesson
    @PostMapping("/generate-otp/{instructorId}/{courseId}/{lessonId}")
    public String generateOtpForLesson(@PathVariable Long instructorId, @PathVariable String courseId, @PathVariable String lessonId) {
        return InstructorService.generateOtpForLesson(instructorId, courseId, lessonId);
    }

    // Student enters OTP to mark attendance
    @PostMapping("/mark-attendance/{studentId}/{courseId}/{lessonId}")
    public boolean markAttendance(@PathVariable Long studentId, @PathVariable String courseId, @PathVariable String lessonId,
                                  @RequestParam String otp) {
        return InstructorService.markAttendance(studentId, courseId, lessonId, otp);
    }

    // Create a course
    @PostMapping("/courses/create")
    public ResponseEntity<Course> createCourse(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int duration) {
        try {
            Course course = AdminService.createCourse( title, description, duration);
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    // Update a course
    @PutMapping("/courses/{courseId}/update")
    public ResponseEntity<Course> updateCourse(
            @PathVariable String courseId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int duration) {
        try {
            Course updatedCourse = AdminService.updateCourse( courseId, title, description, duration);
            return ResponseEntity.ok(updatedCourse);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    // Delete a course
    @DeleteMapping("/courses/{courseId}/delete")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        try {
            boolean isDeleted = AdminService.deleteCourse(courseId);
            if (isDeleted) {
                return ResponseEntity.ok("Course deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/quiz")
    public Quiz createQuiz(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        int totalMarks = (int) payload.get("totalMarks");
        int num = (int) payload.get("num");
        return InstructorService.createQuiz(title,num, totalMarks);
    }

    @PostMapping("/quiz/{quizId}/submit")
    public String submitQuizAnswers(@PathVariable Long quizId, @RequestBody Map<String, Object> payload) {
        Long studentId = ((Number) payload.get("studentId")).longValue();
        Map<String, String> submission = (Map<String, String>) payload.get("answers");

        StudentService.SubmitQuiz(quizId, submission);
        int correctAnswersCount = InstructorService.correctAnswersCount(quizId, studentId);
        return "You got " + correctAnswersCount + " correct answers!";
    }
    // create Questions bank
    @PostMapping("/create/questions")
    public String addQuestions( @RequestBody List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            return "No questions provided!";
        }
        InstructorService.addQuestions(questions);
        return "Questions added successfully!";
    }

    @GetMapping("/quiz/{quizId}")
    public Quiz getQuizById(@PathVariable Long quizId) {
        return StudentService.findQuizById(quizId);
    }

    @GetMapping("/questions")
    public List<Question> getAllQuestions() {

        return StudentService.GetQuestions();
    }

    @GetMapping("/quizzes")
    public List<Quiz> GetAllquizzes() {

        return StudentService.GetAllquizzes();
    }

    @PostMapping("/assignment")
    public Assignment createAssignment(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        return InstructorService.createAssignment(title, description);
    }

    // Submit Assignment
    @PostMapping("/assignment/{assignmentId}/submit")
    public String submitAssignment(@PathVariable Long assignmentId, @RequestBody Map<String, Object> payload) {
        String fileName = (String) payload.get("fileName");
        Long studID = ((Number) payload.get("StudentID")).longValue();
        StudentService.submitAssignment(assignmentId,fileName,studID);
        return "Assignment submitted successfully!";

    }

    @GetMapping("/assignment/{assignmentId}")
    public Assignment getAssignmentById(@PathVariable Long assignmentId) {
        return StudentService.findAssignmentById(assignmentId);
    }

    @GetMapping("/assignments")
    public List<Assignment> GetAllAssignments() {
        return StudentService.GetAllAssignments();
    }


    // Grade Assessment
    @PostMapping("/grade")
    public String gradeAssignment(@RequestBody Map<String, Object> payload) {
        Long studentId = ((Number) payload.get("studentId")).longValue();
        //String type= (String) payload.get("assessmentType");
        String marks = (String) payload.get("marks");
        String feedback = (String) payload.get("feedback");
        InstructorService.gradeAssignment(studentId, "Assignment", marks, feedback);
        return "Assignment graded successfully!";
    }

    // Get Gradings for Student
    @GetMapping("/track/{studentId}")
    public List<Grading> trackStudentPerformance(@PathVariable Long studentId) {
        return InstructorService.trackStudentPerformance(studentId);
    }

    @GetMapping("/assignments/{studentId}")
    public List<Grading>  trackStudentAssignments(@PathVariable Long studentId) {
        return InstructorService.trackStudentAssignments(studentId);
    }


    @GetMapping("/quiz/{studentId}")
    public List<Grading> trackStudentQuizPerformance(@PathVariable Long studentId) {
        return InstructorService.trackStudentQuizPerformance(studentId);
    }
    


}
