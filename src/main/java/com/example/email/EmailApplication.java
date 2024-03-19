package com.example.email;

import com.example.email.entity.Email;
import com.example.email.service.EmailSenderImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EmailApplication {


    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);

    }
}