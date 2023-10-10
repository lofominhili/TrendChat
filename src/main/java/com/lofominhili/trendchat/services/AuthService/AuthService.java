package com.lofominhili.trendchat.services.AuthService;

import com.lofominhili.trendchat.dto.SignInRequest;
import com.lofominhili.trendchat.dto.UserDTO;
import com.lofominhili.trendchat.exceptions.AuthenticationFailedException;

public interface AuthService {
    String signUp(UserDTO request) throws AuthenticationFailedException;

    UserDTO signIn(SignInRequest credentials) throws AuthenticationFailedException;
}


