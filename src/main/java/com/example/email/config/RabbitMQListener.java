package com.example.email.config;
import com.example.email.model.EmailModel;
import com.example.email.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class RabbitMQListener {

    private final EmailService emailService;

    @RabbitListener(queues = "email-queue")
    public void receiveEmailRequest(EmailModel emailModel) {
        log.info("Received email request from RabbitMQ: {}", emailModel);
        try {
            emailService.sendEmail(emailModel);
        } catch (MessagingException e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }
}