package com.example.email.resource;

import com.example.email.model.EmailModel;
import com.example.email.mapper.EmailMapper;
import com.example.email.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class MailResource {

    final EmailService emailService;
    final EmailMapper emailMapper;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailModel emailRequest) throws MessagingException {

        return emailService.sendEmail(emailRequest);
    }
}