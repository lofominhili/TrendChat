package com.lofominhili.trendchat.services.EmailService;

import com.lofominhili.trendchat.entities.UserEntity;
import com.lofominhili.trendchat.services.JwtService.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String CONFIRMATION_LINK = "http://localhost:8080/api/profile/confirm-email/";

    @Value(value = "${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    private final JwtService jwtService;

    @Override
    public void sendConfirmationEmail() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtService.generateToken(user);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Email confirmation");
        simpleMailMessage.setText(String.format("To confirm the registration click this link: %s%s", CONFIRMATION_LINK, token));
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(user.getEmail());
        javaMailSender.send(simpleMailMessage);
    }
}
