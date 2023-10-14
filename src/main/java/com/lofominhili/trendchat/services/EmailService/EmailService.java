package com.lofominhili.trendchat.services.EmailService;


import com.lofominhili.trendchat.entities.UserEntity;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendConfirmationEmail();

    void sendDeactivateAccountEmail();

    SimpleMailMessage createMailMessage(UserEntity user, String token, String subject, String text);
}
