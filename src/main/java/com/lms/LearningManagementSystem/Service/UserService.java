package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Model.User.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, User> userStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1); // Atomic for synchronization
    @Autowired
    @Qualifier("courseService")
    private ICourseService courseService;

    @Autowired
    @Qualifier("notificationService")
    private INotificationService notificationService;

    @Autowired
    private AssessmentService assessmentService;

    public UserService(ICourseService courseService, INotificationService notificationService, AssessmentService assessmentService) {
        this.courseService = courseService;
        this.notificationService = notificationService;
        this.assessmentService = assessmentService;
    }



    // Retrieve all users
    public List<User> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    // Retrieve a user by ID
    public User getUserById(Long id) {
        return userStore.get(id);
    }

    // Add a new user
    public User addUser(User user) {
        // Validate and set role
        if (user.getRole() == null || user.getRole().isEmpty()) {
            throw new IllegalArgumentException("User role is required");
        }

        User newUser;
        switch (user.getRole().toLowerCase()) {
            case "admin":
                newUser = new Admin();
                break;
            case "instructor":
                newUser = new Instructor();
                break;
            case "student":
                newUser = new Student();
                break;
            default:
                throw new IllegalArgumentException("Invalid role. Valid roles are Admin, Instructor, and Student.");
        }

        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        long id = idGenerator.getAndIncrement();
        newUser.setId(id);
        userStore.put(id, newUser);
        return newUser;
    }

    // Update an existing user
    public User updateUser(User updateUser, Long id) {
        if (userStore.containsKey(id)) {
            updateUser.setId(id);
            userStore.put(id, updateUser);
            return updateUser;
        }
        return null;
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        userStore.remove(id);
    }

    public ArrayList<User> listUsers(String role) {
        // Start with all users
        ArrayList<User> users = new ArrayList<>();

        // Filter by role if provided
        if (role != null && !role.isEmpty()) {
            for(User user : userStore.values()){
                if(user.equals(user.getRole() , role)){
                    users.add(user) ;
                }
            }
        }
        return users;
    }
      public boolean enrollInCourse(Long userId, String courseId) {
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
    public List<User> getUsersByRole(String role) {
        List<User> filteredUsers = new ArrayList<>();

        for (User user : userStore.values()) {
            if ("Student".equalsIgnoreCase(role) && user instanceof Student) {
                filteredUsers.add(user);
            } else if ("Instructor".equalsIgnoreCase(role) && user instanceof Instructor) {
                filteredUsers.add(user);
            } else if ("Admin".equalsIgnoreCase(role) && user instanceof Admin) {
                filteredUsers.add(user);
            }
        }

        return filteredUsers;
    }
    public boolean assignInstructorToCourse(Long instructorId, String courseId) {
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


    // Instructor generates OTP for a lesson
    public String generateOtpForLesson(Long instructorId, String courseId, String lessonId) {
        User user = userStore.get(instructorId);
        if (user instanceof Instructor) {
            return courseService.generateOtp(courseId, lessonId);
        }
        return null; // Only instructors can generate OTPs
    }

    // Student enters OTP to mark attendance
    public boolean markAttendance(Long studentId, String courseId, String lessonId, String otp) {
        User user = userStore.get(studentId);
        if (user instanceof Student) {
            return courseService.markAttendance(courseId, lessonId, String.valueOf(studentId), true);
        }
        return false;
    }
    public Course createCourse( String title, String description, int duration) {
            return courseService.createCourse(title, description, duration);
    }
    public Course updateCourse(String courseId, String title, String description, int duration) {
        Course course = courseService.findCourseById(courseId);
        return courseService.updateCourse(courseId,title,description,duration);
    }
    public boolean deleteCourse(String courseId) {
        Course course = courseService.findCourseById(courseId);
        return courseService.deleteCourse(courseId);
    }

       public Quiz createQuiz(String title, int num, int totalMarks) {
        return assessmentService.createQuiz(title, num, totalMarks);
    }

    public void SubmitQuiz(Long quizId, Map<String, String> answers) {
        assessmentService.SubmitQuiz(quizId, answers);
    }

    public int correctAnswersCount(Long quizId, Long studentId) {
        return assessmentService.correctAnswersCount(quizId, studentId);
    }

    // create Questions bank
    public void addQuestions(List<Question> questions) {
        assessmentService.addQuestions(questions); }

    public List<Question> GetQuestions() {
        return assessmentService.GetQuestions();
    }

    // Get Quiz by ID
    public Quiz findQuizById(Long id) {
        return assessmentService.findQuizById(id);
    }

    public List<Quiz> GetAllquizzes() {
        return assessmentService.GetAllquizzes();
    }

    // Create Assignment

    public Assignment createAssignment(String title, String description) {
        return assessmentService.createAssignment(title, description);
    }

    // Submit Assignment
    public void submitAssignment(Long assignmentId, String fileName, Long studID) {
        assessmentService.submitAssignment(assignmentId, fileName, studID);
    }

    // Get Assignment by ID
    public Assignment findAssignmentById(Long id) {
        return assessmentService.findAssignmentById(id);
    }

    public List<Assignment> GetAllAssignments() {
        return assessmentService.GetAllAssignments();
    }

    // Grade Assessment
    public void gradeAssignment(Long studentId, String type, String marks, String feedback) {
        assessmentService.gradeAssignment(studentId, type, marks, feedback);
    }

    // Get Gradings
    public List<Grading> trackStudentPerformance(Long studentId) {
        return assessmentService.trackStudentPerformance(studentId);
    }

    public List<Grading> trackStudentAssignments(Long studentId) {
        return assessmentService.trackStudentAssignments(studentId);
    }

    public List<Grading> trackStudentQuizPerformance(Long studentId) {
        return assessmentService.trackStudentQuizPerformance(studentId);
    }



}
