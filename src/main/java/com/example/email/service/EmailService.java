package com.example.email.service;

import com.example.email.entity.Email;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    Email saveEmail(Email email);
    ResponseEntity<String> sendEmail(Email email);
}
