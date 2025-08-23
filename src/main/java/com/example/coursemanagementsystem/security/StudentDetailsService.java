package com.example.coursemanagementsystem.security;

import com.example.coursemanagementsystem.model.Student;
import com.example.coursemanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Student> studentOptional = studentRepository.findByEmail(email);
        
        if (studentOptional.isEmpty()) {
            throw new UsernameNotFoundException("Student not found with email: " + email);
        }
        
        return studentOptional.get();
    }
}