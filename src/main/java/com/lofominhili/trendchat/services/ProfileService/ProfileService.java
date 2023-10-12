package com.lofominhili.trendchat.services.ProfileService;

import com.lofominhili.trendchat.exceptions.TokenValidationException;

public interface ProfileService {
    String confirmEmail(String token) throws TokenValidationException;

    void sendConfirmation();
}
