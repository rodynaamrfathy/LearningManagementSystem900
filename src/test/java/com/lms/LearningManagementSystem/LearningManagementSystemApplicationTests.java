package com.lms.LearningManagementSystem;

import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

	private CourseService courseService;

	@Mock
	private NotificationService notificationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		courseService = new CourseService(notificationService);
	}

	@Test
	void testCreateCourse() {
		// Arrange
		String title = "Java Programming";
		String description = "Learn the basics of Java.";
		int duration = 30;

		// Act
		Course createdCourse = courseService.createCourse(title, description, duration);

		// Assert
		assertNotNull(createdCourse);
		assertEquals(title, createdCourse.getTitle());
		assertEquals(description, createdCourse.getDescription());
		assertEquals(duration, createdCourse.getDuration());
	}
	@Test
	void testFindCourseById() {
		// Arrange
		String title = "Python Programming";
		String description = "Learn Python.";
		int duration = 25;
		Course createdCourse = courseService.createCourse(title, description, duration);

		// Act
		Course foundCourse = courseService.findCourseById(createdCourse.getId());

		// Assert
		assertNotNull(foundCourse);
		assertEquals(createdCourse.getId(), foundCourse.getId());
	}

	@Test
	void testUpdateCourse() {
		// Arrange
		String title = "Web Development";
		String description = "Learn HTML, CSS, and JS.";
		int duration = 40;
		Course createdCourse = courseService.createCourse(title, description, duration);

		String newTitle = "Advanced Web Development";
		String newDescription = "Master HTML, CSS, and JS.";
		int newDuration = 50;

		// Act
		Course updatedCourse = courseService.updateCourse(
				createdCourse.getId(), newTitle, newDescription, newDuration);

		// Assert
		assertNotNull(updatedCourse);
		assertEquals(newTitle, updatedCourse.getTitle());
		assertEquals(newDescription, updatedCourse.getDescription());
		assertEquals(newDuration, updatedCourse.getDuration());

		// Verify that notifications were sent
		verify(notificationService, times(0)).notifyUser(anyLong(), anyString());
	}

	@Test
	void testDeleteCourse() {
		// Arrange
		String title = "Data Science";
		String description = "Learn Data Analysis.";
		int duration = 60;
		Course createdCourse = courseService.createCourse(title, description, duration);

		// Act
		boolean isDeleted = courseService.deleteCourse(createdCourse.getId());

		// Assert
		assertTrue(isDeleted);

		// Verify the course no longer exists
		assertNull(courseService.findCourseById(createdCourse.getId()));

		// Verify that notifications were sent
		verify(notificationService, times(0)).notifyUser(anyLong(), anyString());
	}
}
