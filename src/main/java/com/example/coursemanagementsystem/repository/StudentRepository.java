package com.example.coursemanagementsystem.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.coursemanagementsystem.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	  Optional<Student> findByEmail(String email);
}
