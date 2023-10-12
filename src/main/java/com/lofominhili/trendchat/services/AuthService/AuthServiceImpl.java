package com.lofominhili.trendchat.services.AuthService;

import com.lofominhili.trendchat.dto.SignInRequest;
import com.lofominhili.trendchat.dto.UserDTO;
import com.lofominhili.trendchat.entities.UserEntity;
import com.lofominhili.trendchat.exceptions.AuthenticationFailedException;
import com.lofominhili.trendchat.mappers.UserMapper;
import com.lofominhili.trendchat.repository.UserRepository;
import com.lofominhili.trendchat.services.JwtService.JwtService;
import com.lofominhili.trendchat.utils.EmailVerificationStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final SecurityContextLogoutHandler logoutHandler;

    @Override
    public void signUp(UserDTO request) throws AuthenticationFailedException {
        if (userRepository.findByUsername(request.getUsername()).isPresent() && (userRepository.findByEmail(request.getEmail())).isPresent()) {
            throw new AuthenticationFailedException("User with prompted credential already exists");
        }
        var user = userMapper.toEntity(request);
        user.setEmailVerificationStatus(EmailVerificationStatus.UNCONFIRMED);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String signIn(SignInRequest credentials) throws AuthenticationFailedException {
        UserEntity user = userRepository.findByEmail(credentials.getEmail()).orElseThrow(() -> new AuthenticationFailedException("Email not found"));
        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException("Wrong credentials");
        }
        return jwtService.generateToken(user);
    }

    @Override
    @Transactional
    public void signOut(HttpServletRequest request) {
        String token = jwtService.getToken(request);
        logoutHandler.logout(request, null, null);
        jwtService.clearWhiteList(jwtService.extractUsername(token));
    }
}
