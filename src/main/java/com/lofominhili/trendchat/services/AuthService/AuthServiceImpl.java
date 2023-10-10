package com.lofominhili.trendchat.services.AuthService;

import com.lofominhili.trendchat.dto.SignInRequest;
import com.lofominhili.trendchat.dto.UserDTO;
import com.lofominhili.trendchat.entities.UserEntity;
import com.lofominhili.trendchat.exceptions.AuthenticationFailedException;
import com.lofominhili.trendchat.mappers.UserMapper;
import com.lofominhili.trendchat.repository.UserRepository;
import com.lofominhili.trendchat.services.JwtService.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public String signUp(UserDTO request) throws AuthenticationFailedException {
        if (userRepository.findByUsername(request.getUsername()).isPresent() && (userRepository.findByEmail(request.getEmail())) != null) {
            throw new AuthenticationFailedException("User with prompted credential already exists");
        }
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    @Override
    public UserDTO signIn(SignInRequest credentials) throws AuthenticationFailedException {
        UserEntity user = userRepository.findByEmail(credentials.getEmail());
        if (user == null || !passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException("Wrong credentials");
        }
        return userMapper.toDto(user);
    }
}
