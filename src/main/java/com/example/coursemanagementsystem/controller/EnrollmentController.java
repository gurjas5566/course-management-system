package com.example.coursemanagementsystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.coursemanagementsystem.model.Course;
import com.example.coursemanagementsystem.model.Enrollment;
import com.example.coursemanagementsystem.model.Student;
import com.example.coursemanagementsystem.service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
	@Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
 // Student Module: Enroll into a course
    @PostMapping("/enroll")
    public ResponseEntity<Enrollment> enrollStudent(@RequestParam Long courseId) {
        Optional<Course> course = courseService.getCourseById(courseId);

        if (course.isPresent()) {
            try {
                Enrollment newEnrollment = enrollmentService.enrollStudentInCourse(course.get());
                return new ResponseEntity<>(newEnrollment, HttpStatus.CREATED);
            } catch (IllegalStateException e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 // Student Module: View enrolled courses for a student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@PathVariable Long studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isPresent()) {
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(student.get());
            return new ResponseEntity<>(enrollments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> dropCourse(@PathVariable Long id) {
        enrollmentService.dropCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
