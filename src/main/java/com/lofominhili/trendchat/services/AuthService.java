package com.lofominhili.trendchat.services;

import com.lofominhili.trendchat.dto.AuthenticationRequest;
import com.lofominhili.trendchat.dto.AuthenticationResponse;
import com.lofominhili.trendchat.dto.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}


