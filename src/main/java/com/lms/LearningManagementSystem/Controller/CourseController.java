package com.lms.LearningManagementSystem.Controller;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.Lesson;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.UserService.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Tested
    // Create a course
    @PostMapping("/{AdminId}/create")
    public ResponseEntity<?> createCourse(
            @PathVariable Long AdminId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int duration,
            @RequestParam String category
            )
    {
        try {
            Course course = AdminService.createCourse(AdminId,title, description, duration, category);
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Operation failed: You are not an admin.", HttpStatus.BAD_REQUEST);
        }
    }

    // Tested
    // Update a course
    @PutMapping("/{AdminId}/{courseId}/update")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long AdminId,
            @PathVariable String courseId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int duration) {
        try {
            Course updatedCourse = AdminService.updateCourse(AdminId ,courseId, title, description, duration);
            return ResponseEntity.ok(updatedCourse);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Operation failed: You are not an admin.", HttpStatus.BAD_REQUEST);
        }
    }

    // Tested
    // Delete a course
    @DeleteMapping("/{AdminId}/{courseId}/delete")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId,@PathVariable Long AdminId) {
        try {
            boolean isDeleted = AdminService.deleteCourse(AdminId,courseId);
            if (isDeleted) {
                return ResponseEntity.ok("Course deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Operation failed: You are not an admin.", HttpStatus.BAD_REQUEST);
        }
    }

    // Tested
    // Add media to a course
    @PostMapping("/{courseId}/media")
    public String addMediaFile(@PathVariable String courseId, @RequestParam String mediaFile) {
        boolean success = courseService.addMediaFile(courseId, mediaFile);
        return success ? "Media file added successfully." : "Failed to add media file.";
    }

    // Tested
    // Add a lesson to a course
    @PostMapping("/{courseId}/lessons")
    public Lesson addLesson(@PathVariable String courseId, @RequestParam String title, @RequestParam String content) {
        return courseService.addLesson(courseId, title, content);
    }

    // BUG
    // View attendance for a lesson
    @GetMapping("/{courseId}/lessons/{lessonId}/attendance")
    public ResponseEntity<Object> getLessonAttendance(@PathVariable String courseId, @PathVariable String lessonId) {
        try {
            Map<String, Boolean> attendance = courseService.getLessonAttendance(courseId, lessonId);
            return ResponseEntity.ok(attendance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Tested
    // View all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }


    // Tested
    // View enrolled students
    @GetMapping("/{courseId}/students")
    public List<Long> getEnrolledStudents(@PathVariable String courseId) {
        return courseService.getEnrolledStudents(courseId);
    }

    // Tested
    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable String courseId) {
        Course course = courseService.findCourseById(courseId);
        if (course != null) {
            return course; // This will include the lessons because they're part of the course object
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCourses(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String instructorName) {

        boolean allEmpty = (keyword == null || keyword.isBlank()) &&
                (category == null || category.isBlank()) &&
                (instructorName == null || instructorName.isBlank());

        if (allEmpty) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("❌ No search parameters provided. Please enter a keyword, category, or instructor name.");
        }

        List<Course> results = courseService.searchAndFilter(keyword, category, instructorName);

        if (results.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("🔍 No courses found matching the given criteria.");
        }

        return ResponseEntity.ok(results);
    }





}
