package com.example.email.controller;

import com.example.email.dto.EmailRequest;
import com.example.email.entity.Email;
import com.example.email.job.EmailJob;
import com.example.email.mapper.EmailMapper;
import com.example.email.service.EmailService;
import jakarta.validation.Valid;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class MailController {
    @Autowired
    EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {

        return emailService.sendEmail(EmailMapper.requestToEntity(emailRequest));
    }
}