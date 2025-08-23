package com.example.coursemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.coursemanagementsystem.model.Enrollment;
import com.example.coursemanagementsystem.model.Student;
import com.example.coursemanagementsystem.model.Course;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	List<Enrollment> findByStudent(Student student);

    // This method checks if a student is already enrolled in a specific course
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);
}
