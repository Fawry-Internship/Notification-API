package com.example.email.config;
import com.example.email.model.EmailModel;
import com.example.email.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class RabbitMQListener {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "notificationQueue")
    public void processEmailMessage(String jsonEmailModel) {
        try {
            EmailModel emailModel = objectMapper.readValue(jsonEmailModel, EmailModel.class);
            log.info("Received email message from RabbitMQ: {}", emailModel);
            emailService.sendEmail(emailModel);
        } catch (Exception e) {
            log.error("Error processing email message from RabbitMQ: {}", e.getMessage());
        }
    }

}