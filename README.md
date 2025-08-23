# Smart Course Management System

A full-stack web application for managing courses, students, and enrollments.  
This project demonstrates a secure, layered architecture using modern Java and web technologies.

---

##  Features

### 1. Admin Module
- **Course Management**: Create, view, update, and delete courses.  
- **Student Overview**: View all registered students.  
- **Reporting**: Generate and download text-based report cards for students.  

### 2. Student Module
- **Registration/Login**: Secure user authentication.  
- **Enrollment**: View available courses and enroll in them.  
- **Course Management**: View a list of enrolled courses and drop a course.  

### 3. Additional Features
- **Email Integration**: Sends an enrollment confirmation email to students.  
- **File-Based Logging**: Logs student actions to a file for auditing.  
- **Role-Based Security (Planned Enhancement)**: Restricts access to specific endpoints based on user roles (Admin vs. Student).  

---

##  Technologies Used

### Backend
- **Java 17**: Core programming language  
- **Spring Boot**: Application framework  
- **Spring Data JPA**: Database interaction and ORM  
- **Spring Security**: Authentication and authorization  
- **JavaMailSender**: Sending email notifications  
- **MySQL**: Relational database for data persistence  

### Frontend
- **HTML5, CSS3, JavaScript**: Static web pages and dynamic interactions  
- **Fetch API**: For making asynchronous calls to the backend REST API  

### Tools
- **Postman**: Used for sending requests and testing the backend REST API  
