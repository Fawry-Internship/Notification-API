package com.example.email.service.impl;

import com.example.email.entity.Email;
import com.example.email.entity.Status;
import com.example.email.mapper.EmailMapper;
import com.example.email.model.EmailModel;
import com.example.email.repository.MailRepository;
import com.example.email.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final MailRepository mailRepo;
    private final JavaMailSender mailSender;
    private final EmailMapper emailMapper;

    @Override
    public ResponseEntity<String> sendEmail(EmailModel emailModel) {
        log.info("You want to send this email: {}", emailModel);
        Email email = emailMapper.modelToEntity(emailModel);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
        try {
            messageHelper.setSubject(email.getSubject());
            String body = generateHtmlContent(email.getProduct(), email.getPrice(), emailModel.getCreation().toString());
            messageHelper.setText(body, true);
            messageHelper.setFrom("${spring.mail.username}");
            messageHelper.setTo(email.getTo());
            mailSender.send(message);
            email.setStatus(Status.success);
            mailRepo.save(email);
            log.info("Email sent successfully!");
            return ResponseEntity.ok("Email sent successfully!");
        } catch (MailException | MessagingException e) {
            email.setStatus(Status.faild);
            mailRepo.save(email);
            log.error("Failed to send email: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }

    private String generateHtmlContent(String productName, String price, String creation) {
        String emailContent = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; }"
                + ".email-container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #f4f4f4; }"
                + ".header { background-color: #4CAF50; color: white; text-align: center; padding: 10px; }"
                + ".content { padding: 20px; }"
                + ".thank-you { font-size: 18px; font-weight: bold; color: #4CAF50; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='email-container'>"
                + "<div class='header'><h2>Order Confirmation</h2></div>"
                + "<div class='content'>"
                + "<p>Hi there,</p>"
                + "<p>Created at: " + creation + "</p>"
                + "<p>Order item: " + productName + "</p>"
                + "<p>Price: " + price + "</p>"
                + "<p class='thank-you'>Have A Nice Day ;-D!</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        return emailContent;
    }
}
