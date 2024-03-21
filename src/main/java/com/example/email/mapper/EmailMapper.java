package com.example.email.mapper;

import com.example.email.dto.EmailRequest;
import com.example.email.entity.Email;

public class EmailMapper {
    public static Email requestToEntity(EmailRequest emailRequest) {
        Email email = new Email();
        email.setTo(emailRequest.getTo());
        email.setFrom("");
        email.setSubject(emailRequest.getSubject());
        email.setProduct(emailRequest.getProduct());
        email.setPrice(emailRequest.getPrice());
        email.setCreation(emailRequest.getCreation());
        return email;
    }
}
