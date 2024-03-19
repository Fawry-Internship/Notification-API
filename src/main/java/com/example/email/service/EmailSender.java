package com.example.email.service;

import com.example.email.entity.Email;

public interface EmailSender {
    Email saveEmail(Email email);
}
