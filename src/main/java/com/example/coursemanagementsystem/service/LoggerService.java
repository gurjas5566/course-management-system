package com.example.coursemanagementsystem.service;

import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Service
public class LoggerService {
	private static final String LOG_FILE_PATH = "student_actions.log";

    public void log(String message) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            
            String logEntry = String.format("[%s] %s", new Date(), message);
            printWriter.println(logEntry);
            System.out.println("LOGGED: " + logEntry); 
            
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
