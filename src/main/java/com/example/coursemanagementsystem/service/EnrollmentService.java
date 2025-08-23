package com.example.coursemanagementsystem.service;

import com.example.coursemanagementsystem.email.EmailService;
import com.example.coursemanagementsystem.model.Course;
import com.example.coursemanagementsystem.model.Enrollment;
import com.example.coursemanagementsystem.model.Student;
import com.example.coursemanagementsystem.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private LoggerService loggerService;
	@Autowired
	private EmailService emailService;


	public List<Enrollment> getAllEnrollments() {
		return enrollmentRepository.findAll();
	}

	public List<Enrollment> getEnrollmentsByStudent(Student student) {
		return enrollmentRepository.findByStudent(student);
	}

	public Enrollment enrollStudentInCourse(Course course) {
		// Get the authenticated student from the Security Context
		Student student = getAuthenticatedStudent();
		
		if (enrollmentRepository.findByStudentAndCourse(student, course).isPresent()) {
			throw new IllegalStateException("Student is already enrolled in this course.");
		}
		
		Enrollment enrollment = new Enrollment();
		enrollment.setStudent(student);
		enrollment.setCourse(course);
		enrollment.setEnrollmentDate(new Date());

		Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
		String logMessage = String.format(
				"Student %s (ID: %d) enrolled in course '%s' (ID: %d)",
				student.getFirstName() + " " + student.getLastName(),
				student.getStudentId(),
				course.getCourseName(),
				course.getCourseId()
		);
		loggerService.log(logMessage);
		emailService.sendEnrollmentConfirmation(student.getEmail(), student.getFirstName(), course.getCourseName());


		return savedEnrollment;
	}
	public void dropCourse(long enrollmentId)
	{
		enrollmentRepository.deleteById(enrollmentId);
		 loggerService.log("Enrollment ID: " + enrollmentId + " has been dropped.");
	}
	private Student getAuthenticatedStudent() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof Student) {
			return (Student) authentication.getPrincipal();
		}
		throw new IllegalStateException("No authenticated student found.");
	}

}