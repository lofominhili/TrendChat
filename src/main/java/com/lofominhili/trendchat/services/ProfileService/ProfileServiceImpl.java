package com.lofominhili.trendchat.services.ProfileService;

import com.lofominhili.trendchat.dto.RequestDTO.UpdatePasswordRequest;
import com.lofominhili.trendchat.dto.RequestDTO.UpdateProfileRequest;
import com.lofominhili.trendchat.entities.UserEntity;
import com.lofominhili.trendchat.exceptions.RequestDataValidationFailedException;
import com.lofominhili.trendchat.exceptions.TokenValidationException;
import com.lofominhili.trendchat.repository.UserRepository;
import com.lofominhili.trendchat.services.AuthService.AuthService;
import com.lofominhili.trendchat.services.EmailService.EmailService;
import com.lofominhili.trendchat.services.JwtService.JwtService;
import com.lofominhili.trendchat.utils.ActivationStatus;
import com.lofominhili.trendchat.utils.EmailVerificationStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

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
    @Transactional
    public String updateProfile(UpdateProfileRequest request) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = (request.getName() == null || request.getName().isBlank()) ? user.getName() : request.getName();
        String surname = (request.getSurname() == null || request.getSurname().isBlank()) ? user.getSurname() : request.getSurname();
        String email = (request.getEmail() == null || request.getEmail().isBlank()) ? user.getEmail() : request.getEmail();
        user.setName(name);
        user.setSurname(surname);
        boolean hasChanged = !user.getEmail().equals(email);
        user.setEmail(email);
        if (hasChanged)
            user.setEmailVerificationStatus(EmailVerificationStatus.UNCONFIRMED);
        String token = "";
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            jwtService.clearWhiteList(user.getUsername());
            user.setUsername(request.getUsername());
            token = jwtService.generateToken(user);
        }
        userRepository.save(user);
        if (hasChanged)
            sendConfirmation();
        return token.isBlank() ? "" : token;
    }

    @Override
    @Transactional
    public String updatePassword(UpdatePasswordRequest request) throws RequestDataValidationFailedException {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RequestDataValidationFailedException("Invalid password, please enter the correct old password!");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        jwtService.clearWhiteList(user.getUsername());
        String token = jwtService.generateToken(user);
        userRepository.save(user);
        return token;
    }

    @Override
    @Transactional
    public String deleteAccount(HttpServletRequest request) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setActivationStatus(ActivationStatus.DEACTIVATED);
        emailService.sendDeactivateAccountEmail();
        userRepository.save(user);
        authService.signOut(request);
        return user.getEmail();
    }

    @Override
    @Transactional
    public String restoreAccount(String token) {
        String username = jwtService.extractUsername(token);
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        user.setActivationStatus(ActivationStatus.ACTIVATED);
        userRepository.save(user);
        jwtService.deleteTokenInWhiteList(token);
        return user.getUsername();
    }

    @Override
    public void sendConfirmation() {
        emailService.sendConfirmationEmail();
    }
}
