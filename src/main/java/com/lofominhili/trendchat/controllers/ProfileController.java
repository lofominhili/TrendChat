package com.lofominhili.trendchat.controllers;

import com.lofominhili.trendchat.dto.BasicDto.SuccessDTO;
import com.lofominhili.trendchat.dto.RequestDTO.UpdatePasswordRequest;
import com.lofominhili.trendchat.dto.RequestDTO.UpdateProfileRequest;
import com.lofominhili.trendchat.exceptions.RequestDataValidationFailedException;
import com.lofominhili.trendchat.exceptions.TokenValidationException;
import com.lofominhili.trendchat.services.ProfileService.ProfileService;
import com.lofominhili.trendchat.utils.GlobalExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "Confirm email by token")
    @GetMapping(value = "/confirm-email/{token}")
    public ResponseEntity<SuccessDTO<String>> sendConfirmation(@PathVariable String token) throws TokenValidationException {
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Email confirmed",
                        profileService.confirmEmail(token)
                ), HttpStatus.OK);
    }

    @Operation(summary = "Send email confirmation")
    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/send-confirmation")
    public ResponseEntity<SuccessDTO<String>> sendConfirmation() {
        profileService.sendConfirmation();
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Email sent",
                        "Check your mailbox"
                ), HttpStatus.OK);
    }

    @Operation(summary = "Update user profile")
    @SecurityRequirement(name = "JWT")
    @PutMapping(value = "/update-profile")
    public ResponseEntity<SuccessDTO<String>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            BindingResult validationResult
    ) throws RequestDataValidationFailedException {
        if (validationResult.hasErrors()) {
            throw new RequestDataValidationFailedException(GlobalExceptionHandler.handleValidationResults(validationResult));
        }
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Update profile",
                        String.format("Successfully updated!%s", profileService.updateProfile(request))
                ), HttpStatus.OK);

    }

    @Operation(summary = "Update user password")
    @SecurityRequirement(name = "JWT")
    @PutMapping(value = "/update-password")
    public ResponseEntity<SuccessDTO<String>> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest request,
            BindingResult validationResult
    ) throws RequestDataValidationFailedException {
        if (validationResult.hasErrors()) {
            throw new RequestDataValidationFailedException(GlobalExceptionHandler.handleValidationResults(validationResult));
        }
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Update password",
                        profileService.updatePassword(request)
                ), HttpStatus.OK);

    }

    @Operation(summary = "Deactivate user account")
    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/deactivate-account")
    public ResponseEntity<SuccessDTO<String>> deactivateAccount(HttpServletRequest request) {
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Deactivate account",
                        String.format("Check all info here: %s", profileService.deleteAccount(request))
                ), HttpStatus.OK
        );
    }

    @Operation(summary = "Reactivate user account")
    @GetMapping(value = "/reactivate-account/{token}")
    public ResponseEntity<SuccessDTO<String>> reactivateAccount(@PathVariable String token) {
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Reactivate account",
                        profileService.restoreAccount(token)
                ), HttpStatus.OK
        );
    }


}
