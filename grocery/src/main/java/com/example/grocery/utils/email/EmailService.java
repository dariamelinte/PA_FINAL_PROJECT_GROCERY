package com.example.grocery.utils.email;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendMail(String from, String to, String subject, String content) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(from);
        email.setTo(to);

        email.setSubject(subject);
        email.setText(content);

        mailSender.send(email);
    }
}
