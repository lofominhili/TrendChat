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
    private static final String DEACTIVATION_LINK = "http://localhost:8080/api/profile/reactivate-account/";


    @Value(value = "${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    private final JwtService jwtService;

    @Override
    public void sendConfirmationEmail() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtService.generateToken(user);
        SimpleMailMessage simpleMailMessage = createMailMessage(user, token, "Email confirmation",
                String.format("To confirm the registration click this link: %s%s", CONFIRMATION_LINK, token));
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendDeactivateAccountEmail() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtService.generateToken(user);
        SimpleMailMessage simpleMailMessage = createMailMessage(user, token, "Account deactivation",
                String.format("It looks like you have decided to deactivate your account, \n" +
                        "Well, if you want to restore, here is the link for it(it will be available within 14 days): %s%s", DEACTIVATION_LINK, token));
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public SimpleMailMessage createMailMessage(UserEntity user, String token, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(user.getEmail());
        return simpleMailMessage;
    }
}
