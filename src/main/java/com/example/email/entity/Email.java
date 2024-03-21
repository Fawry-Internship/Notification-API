package com.example.email.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="reciver")
    private String to;
    @Column(name = "sender")
    private String from;
    private String subject;
    private String product;
    private String price;
    private String creation;
    private boolean send;

}
