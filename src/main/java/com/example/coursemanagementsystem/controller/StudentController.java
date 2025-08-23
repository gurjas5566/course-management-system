package com.example.coursemanagementsystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.coursemanagementsystem.model.Student;
import com.example.coursemanagementsystem.service.StudentService;
import com.example.coursemanagementsystem.service.StudentService;
import java.io.IOException;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")

public class StudentController {

	@Autowired
    private StudentService studentService;
	
	// Admin Module: Add a new student
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student createdStudent = studentService.addStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    // Admin Module: Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    // Admin/Student Module: Get a single student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
 // Admin Module: Delete a student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 // Admin Module: Generate a report card for a student
    @GetMapping("/{id}/report")
    public ResponseEntity<String> generateReportCard(@PathVariable Long id) {
        Optional<String> filePath = studentService.generateReportCard(id);
        
        if (filePath.isPresent()) {
            String successMessage = "Report card generated successfully. File: " + filePath.get();
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to generate report card.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 // Admin Module: Update a student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> studentOptional = studentService.getStudentById(id);

        if (studentOptional.isPresent()) {
            Student existingStudent = studentOptional.get();
            // Update the fields you want to change
            existingStudent.setFirstName(studentDetails.getFirstName());
            existingStudent.setLastName(studentDetails.getLastName());
            existingStudent.setEmail(studentDetails.getEmail());
            // Note: Password update should be handled in a separate, secure process
            
            Student updatedStudent = studentService.addStudent(existingStudent); // save() and update() are the same
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
