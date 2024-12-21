package com.lms.LearningManagementSystem.Controller;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.Lesson;
import com.lms.LearningManagementSystem.Service.CourseService;
import org.springframework.http.HttpStatus;
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

    // Add media to a course
    @PostMapping("/{courseId}/media")
    public String addMediaFile(@PathVariable String courseId, @RequestParam String mediaFile) {
        boolean success = courseService.addMediaFile(courseId, mediaFile);
        return success ? "Media file added successfully." : "Failed to add media file.";
    }

    // Add a lesson to a course
    @PostMapping("/{courseId}/lessons")
    public Lesson addLesson(@PathVariable String courseId, @RequestParam String title, @RequestParam String content) {
        return courseService.addLesson(courseId, title, content);
    }


    // Mark attendance for a lesson
    @PostMapping("/{courseId}/lessons/{lessonId}/attendance")
    public String markAttendance(@PathVariable String courseId, @PathVariable String lessonId,
                                 @RequestParam String studentId, @RequestParam boolean present) {
        boolean success = courseService.markAttendance(courseId, lessonId, studentId, present);
        return success ? "Attendance marked successfully." : "Failed to mark attendance.";
    }

    // View attendance for a lesson
    @GetMapping("/{courseId}/lessons/{lessonId}/attendance")
    public Map<String, Boolean> getLessonAttendance(@PathVariable String courseId, @PathVariable String lessonId) {
        return courseService.getLessonAttendance(courseId, lessonId);
    }

    // View all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // View enrolled students
    @GetMapping("/{courseId}/students")
    public List<Long> getEnrolledStudents(@PathVariable String courseId) {
        return courseService.getEnrolledStudents(courseId);
    }
    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable String courseId) {
        Course course = courseService.findCourseById(courseId);
        if (course != null) {
            return course; // This will include the lessons because they're part of the course object
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
    }

}