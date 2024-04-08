package com.example.email.service.impl;

import com.example.email.entity.Email;
import com.example.email.entity.Status;
import com.example.email.mapper.EmailMapper;
import com.example.email.model.EmailModel;
import com.example.email.repository.MailRepository;
import com.example.email.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final MailRepository mailRepo;
    private final JavaMailSender mailSender;
    private final EmailMapper emailMapper;
    @Override
    public ResponseEntity<String> sendEmail(EmailModel emailModel) throws RuntimeException, MessagingException {

        Email email=emailMapper.modelToEntity(emailModel);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
        messageHelper.setSubject(email.getSubject());
        String body = HtmlContent(email.getProduct(), email.getPrice(),emailModel.getCreation().toString());
        messageHelper.setText(body, true);
        messageHelper.setFrom("${spring.mail.username}");
        messageHelper.setTo(email.getTo());

        try {
            mailSender.send(message);
            email.setStatus(Status.success);
            mailRepo.save(email);

            return ResponseEntity.ok("Email send successfully!");
        } catch (MailException e) {
            email.setStatus(Status.faild);
            mailRepo.save(email);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email failed to send");

        }


    }
    private String HtmlContent(String product, String price, String creation) {
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
                + "<p>Dear Customer " + ",</p>"
                + "<p>Created at: " + creation + "</p>"
                + "<p>Order item: " + product + "</p>"
                + "<p>Price: " + price + "</p>"
                + "<p class='thank-you'>Thanks for your order!</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        return emailContent;
    }
}
