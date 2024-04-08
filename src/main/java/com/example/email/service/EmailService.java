package com.example.email.service;

import com.example.email.entity.Email;
import com.example.email.model.EmailModel;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<String> sendEmail(EmailModel emailModel) throws MessagingException;
}
