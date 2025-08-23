package com.example.coursemanagementsystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.coursemanagementsystem.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
