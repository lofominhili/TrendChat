package com.lofominhili.trendchat.services.ProfileService;

import com.lofominhili.trendchat.entities.UserEntity;
import com.lofominhili.trendchat.exceptions.TokenValidationException;
import com.lofominhili.trendchat.repository.UserRepository;
import com.lofominhili.trendchat.services.EmailService.EmailService;
import com.lofominhili.trendchat.services.JwtService.JwtService;
import com.lofominhili.trendchat.utils.EmailVerificationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    @Transactional
    public String confirmEmail(String token) throws TokenValidationException {
        UserEntity userEntity = userRepository.findByUsername(jwtService.extractUsername(token))
                .orElseThrow(() -> new TokenValidationException("Invalid token"));
        if (jwtService.isTokenInWhiteList(token)) {
            userEntity.setEmailVerificationStatus(EmailVerificationStatus.CONFIRMED);
            userRepository.save(userEntity);
            jwtService.deleteTokenInWhiteList(token);
            return userEntity.getEmail();
        }
        throw new TokenValidationException("Invalid token");
    }

    @Override
    public void sendConfirmation() {
        emailService.sendConfirmationEmail();
    }
}
