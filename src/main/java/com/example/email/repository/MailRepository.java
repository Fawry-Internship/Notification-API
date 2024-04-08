package com.example.email.repository;

import com.example.email.entity.Email;
import com.example.email.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Email,Long> {
     List<Email> findByStatus(Status status);
}
