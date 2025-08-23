package com.example.coursemanagementsystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.coursemanagementsystem.model.Course;
import com.example.coursemanagementsystem.service.CourseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
	@Autowired
	private CourseService courseService;

	// Admin Module: Add a new course
	@PostMapping
	public ResponseEntity<Course> createCourse(@RequestBody Course course)
	{
		Course createdCourse = courseService.addCourse(course);
		return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
	}
	// Admin/Student Module: Get all courses
	@GetMapping
	public ResponseEntity<List<Course>> getAllCourses() {
		List<Course> courses = courseService.getAllCourses();
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

	// Admin/Student Module: Get a single course by ID
	@GetMapping("/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
		Optional<Course> course = courseService.getCourseById(id);
		return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	// Admin Module: Update a course
	@PutMapping("/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
		Optional<Course> course = courseService.getCourseById(id);
		if (course.isPresent()) {
			Course existingCourse = course.get();
			existingCourse.setCourseName(courseDetails.getCourseName());
			existingCourse.setCourseDescription(courseDetails.getCourseDescription());
			existingCourse.setCourseCode(courseDetails.getCourseCode());
			Course updatedCourse = courseService.addCourse(existingCourse);
			return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	// Admin Module: Delete a course
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
		courseService.deleteCourse(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
