package com.example.email.job;

import com.example.email.entity.Email;
import com.example.email.entity.Status;
import com.example.email.mapper.EmailMapper;
import com.example.email.model.EmailModel;
import com.example.email.repository.MailRepository;
import com.example.email.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailJob {

    private final EmailService emailService;

    private final MailRepository mailRepository;
    EmailMapper emailMapper;

    @Scheduled(fixedRate = 600000)//every 10 min
    public void ResendFailedEmail() throws MessagingException {
        List<Email> failedEmail = mailRepository.findByStatus(Status.faild);
        for (Email email : failedEmail) {
            EmailModel emailModel=emailMapper.entityToModel(email);
            emailService.sendEmail(emailModel);
        }

    }


}