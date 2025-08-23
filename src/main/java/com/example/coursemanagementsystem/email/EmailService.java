package com.example.coursemanagementsystem.email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEnrollmentConfirmation(String toEmail, String studentName, String courseName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gurjassingh5046@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Enrollment Confirmation");
        message.setText(String.format("Hello %s,\n\nYou have been successfully enrolled in the course: %s.\n\nThank you!", studentName, courseName));
        
        mailSender.send(message);
    }
}
