package com.example.email.service;

import com.example.email.entity.Email;
import com.example.email.repo.MailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderImp implements EmailSender {

    @Autowired
    MailRepo mailRepo;
    @Override
    public Email saveEmail(Email email) {
        return mailRepo.save(email);
    }
}
