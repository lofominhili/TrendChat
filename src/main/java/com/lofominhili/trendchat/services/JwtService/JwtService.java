package com.lofominhili.trendchat.services.JwtService;

import com.lofominhili.trendchat.exceptions.TokenValidationException;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails) throws TokenValidationException;

    @Nullable
    String getToken(HttpServletRequest request);

    boolean isTokenInWhiteList(String token);

    void clearWhiteList(String username);

    void deleteTokenInWhiteList(String token);

}
