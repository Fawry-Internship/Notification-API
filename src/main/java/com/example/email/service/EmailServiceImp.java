package com.example.email.service;

import com.example.email.entity.Email;
import com.example.email.job.EmailJob;
import com.example.email.mapper.EmailMapper;
import com.example.email.repo.MailRepo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    MailRepo mailRepo;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public Email saveEmail(Email email) {
        return mailRepo.save(email);
    }
    @Override
    public ResponseEntity<String> sendEmail(Email email){
        try {
            JobDetail jobDetail = JobBuilder.newJob(EmailJob.class)
                    .withIdentity("emailJob_" + email.hashCode())
                    .usingJobData("to", email.getTo())
                    .usingJobData("subject", email.getSubject())
                    .usingJobData("product", email.getProduct())
                    .usingJobData("price", email.getPrice())
                    .usingJobData("creation", email.getCreation())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("emailTrigger_" + email.hashCode())
                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);

            return ResponseEntity.ok("Email sending job scheduled successfully!");
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error scheduling email sending job");
        }

    }
}
