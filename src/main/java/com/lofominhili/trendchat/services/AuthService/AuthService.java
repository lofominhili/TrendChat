package com.lofominhili.trendchat.services.AuthService;

import com.lofominhili.trendchat.dto.RequestDTO.SignInRequest;
import com.lofominhili.trendchat.dto.UserDTO;
import com.lofominhili.trendchat.exceptions.AuthenticationFailedException;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    void signUp(UserDTO request) throws AuthenticationFailedException;

    String signIn(SignInRequest credentials) throws AuthenticationFailedException;

    void signOut(HttpServletRequest request);
}


