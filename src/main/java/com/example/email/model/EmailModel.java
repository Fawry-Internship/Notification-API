package com.example.email.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailModel {
    @Email
    @NotNull
    private String To;
    @NotNull
    private String subject;
    @NotNull
    private String product;
    @NotNull
    private String price;
    @NotNull
    private LocalDate creation;
}
