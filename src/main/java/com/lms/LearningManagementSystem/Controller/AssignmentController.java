package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.Assessment.Assignment;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import com.lms.LearningManagementSystem.Service.UserService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Assignment createAssignment(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        return instructorService.createAssignment(title, description);
    }

    @PostMapping("/{assignmentId}/submit")
    public String submitAssignment(@PathVariable Long assignmentId, @RequestBody Map<String, Object> payload) {
        String fileName = (String) payload.get("fileName");
        Long studID = ((Number) payload.get("StudentID")).longValue();
        studentService.submitAssignment(assignmentId, fileName, studID);
        return "Assignment submitted successfully!";
    }

    @GetMapping("/{assignmentId}")
    public Assignment getAssignmentById(@PathVariable Long assignmentId) {
        return studentService.findAssignmentById(assignmentId);
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return studentService.GetAllAssignments();
    }
}
