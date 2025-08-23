package com.example.coursemanagementsystem.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.coursemanagementsystem.model.Course;
import com.example.coursemanagementsystem.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
	@Autowired
    private CourseRepository courseRepository;
	 // Admin Module: Add / Update / Delete Courses
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}

