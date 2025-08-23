package com.example.coursemanagementsystem.model;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="students")

public class Student implements UserDetails {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long studentId;
	
		@Column(name = "first_name", nullable = false)
		private String firstName;
	 
		@Column(name = "last_name", nullable = false)
	    private String lastName;

	    @Column(name = "email", nullable = false, unique = true)
	    private String email;

	    @Column(name = "password_hash", nullable = false)
	    private String passwordHash;
	    
	    public Student() {
	    }

	    // Getters and setters
	    public Long getStudentId() {
	        return studentId;
	    }

	    public void setStudentId(Long studentId) {
	        this.studentId = studentId;
	    }

	    public String getFirstName() {
	        return firstName;
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }

	    public String getLastName() {
	        return lastName;
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPasswordHash() {
	        return passwordHash;
	    }

	    public void setPasswordHash(String passwordHash) {
	        this.passwordHash = passwordHash;
	    }
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities()
	    {
	    	return Collections.emptyList(); 
	    }

	    @Override
	    public String getPassword() {
	        return this.passwordHash;
	    }

	    @Override
	    public String getUsername() {
	        return this.email; // We will use email as the username
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
	
}
