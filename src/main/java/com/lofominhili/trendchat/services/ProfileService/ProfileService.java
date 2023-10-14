package com.lofominhili.trendchat.services.ProfileService;

import com.lofominhili.trendchat.dto.RequestDTO.UpdatePasswordRequest;
import com.lofominhili.trendchat.dto.RequestDTO.UpdateProfileRequest;
import com.lofominhili.trendchat.exceptions.RequestDataValidationFailedException;
import com.lofominhili.trendchat.exceptions.TokenValidationException;
import jakarta.servlet.http.HttpServletRequest;

public interface ProfileService {
    String confirmEmail(String token) throws TokenValidationException;

    void sendConfirmation();

    String updateProfile(UpdateProfileRequest request);

    String updatePassword(UpdatePasswordRequest request) throws RequestDataValidationFailedException;

    String deleteAccount(HttpServletRequest request);

    String restoreAccount(String token);
}
