package com.example.coursemanagementsystem.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.coursemanagementsystem.model.Enrollment;
import com.example.coursemanagementsystem.model.Student;
import com.example.coursemanagementsystem.repository.StudentRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	@Autowired
	private EnrollmentService enrollmentService; // Inject the EnrollmentService
	@Autowired
	private LoggerService loggerService; // Inject the LoggerService

	public Student addStudent(Student student) {
		// Here we could add logic like password hashing before saving
		student.setPasswordHash(passwordEncoder.encode(student.getPasswordHash()));
		return studentRepository.save(student);
	}

	public Optional<Student> getStudentById(Long studentId) {
		return studentRepository.findById(studentId);
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public void deleteStudent(Long studentId) {
		studentRepository.deleteById(studentId);
	}
	 public Optional<String> generateReportCard(Long studentId) {
	        Optional<Student> studentOptional = studentRepository.findById(studentId);

	        if (studentOptional.isPresent()) {
	            Student student = studentOptional.get();
	            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(student);

	            String filePath = String.format("report_card_student_%d.txt", student.getStudentId());

	            try (FileWriter fileWriter = new FileWriter(filePath);
	                 PrintWriter printWriter = new PrintWriter(fileWriter)) {

	                printWriter.println("------------------------------------");
	                printWriter.println("          STUDENT REPORT CARD       ");
	                printWriter.println("------------------------------------");
	                printWriter.println("Student Name: " + student.getFirstName() + " " + student.getLastName());
	                printWriter.println("Student ID: " + student.getStudentId());
	                printWriter.println("Email: " + student.getEmail());
	                printWriter.println("------------------------------------");
	                printWriter.println("Enrolled Courses:");
	                printWriter.println("------------------------------------");

	                if (enrollments.isEmpty()) {
	                    printWriter.println("No courses enrolled.");
	                } else {
	                    for (Enrollment enrollment : enrollments) {
	                        printWriter.println("- " + enrollment.getCourse().getCourseName() +
	                                " (" + enrollment.getCourse().getCourseCode() + ")");
	                    }
	                }
	                printWriter.println("------------------------------------");
	                
	                loggerService.log("Report card generated for student ID: " + studentId);
	                
	                return Optional.of(filePath);

	            } catch (IOException e) {
	                loggerService.log("Failed to generate report card for student ID: " + studentId + " - " + e.getMessage());
	                return Optional.empty();
	            }
	        }
	        return Optional.empty();
	    }
	}


