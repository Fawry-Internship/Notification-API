package com.example.email.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

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
    private LocalDate creation;
    @Enumerated(EnumType.STRING)
    private Status status;

}
