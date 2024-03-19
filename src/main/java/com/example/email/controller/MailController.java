package com.example.email.controller;

import com.example.email.dto.EmailRequest;
import com.example.email.entity.Email;
import com.example.email.job.EmailJob;
import com.example.email.service.EmailSender;
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
    EmailSender emailSender;
    @Autowired
    private Scheduler scheduler;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(EmailJob.class)
                    .withIdentity("emailJob_" + emailRequest.hashCode())
                    .usingJobData("to", emailRequest.getTo())
                    .usingJobData("subject", emailRequest.getSubject())
                    .usingJobData("product", emailRequest.getProduct())
                    .usingJobData("price",emailRequest.getPrice())
                    .usingJobData("creation", emailRequest.getCreation())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("emailTrigger_" + emailRequest.hashCode())
                    .startNow()
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            Email email=new Email();
            email.setTo(emailRequest.getTo());
            email.setSubject(emailRequest.getSubject());
            email.setProduct(emailRequest.getProduct());
            email.setPrice(emailRequest.getPrice());
            emailSender.saveEmail(email);
            return ResponseEntity.ok("Email sending job scheduled successfully!");
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error scheduling email sending job");
        }
    }
}