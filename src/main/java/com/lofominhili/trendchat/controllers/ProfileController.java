package com.lofominhili.trendchat.controllers;

import com.lofominhili.trendchat.dto.BasicDto.SuccessDTO;
import com.lofominhili.trendchat.dto.RequestDTO.UpdatePasswordRequest;
import com.lofominhili.trendchat.dto.RequestDTO.UpdateProfileRequest;
import com.lofominhili.trendchat.exceptions.RequestDataValidationFailedException;
import com.lofominhili.trendchat.exceptions.TokenValidationException;
import com.lofominhili.trendchat.services.ProfileService.ProfileService;
import com.lofominhili.trendchat.utils.GlobalExceptionHandler;
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

    @GetMapping(value = "/confirm-email/{token}")
    public ResponseEntity<SuccessDTO<String>> confirmEmail(@PathVariable String token) throws TokenValidationException {
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Email confirmed",
                        profileService.confirmEmail(token)
                ), HttpStatus.OK);
    }

    @PostMapping(value = "/send-confirmation")
    public ResponseEntity<SuccessDTO<String>> confirmEmail() {
        profileService.sendConfirmation();
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Email sent",
                        "Check your mailbox"
                ), HttpStatus.OK);
    }

    @PutMapping(value = "/update-profile")
    public ResponseEntity<SuccessDTO<String>> editUser(
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

    @PutMapping(value = "/update-password")
    public ResponseEntity<SuccessDTO<String>> editUser(
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

    @PostMapping(value = "/deactivate-account")
    public ResponseEntity<SuccessDTO<String>> disableAccount(HttpServletRequest request) {
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Deactivate account",
                        String.format("Check all info here: %s", profileService.deleteAccount(request))
                ), HttpStatus.OK
        );
    }

    @GetMapping(value = "/reactivate-account/{token}")
    public ResponseEntity<SuccessDTO<String>> enableAccount(@PathVariable String token) {
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Reactivate account",
                        profileService.restoreAccount(token)
                ), HttpStatus.OK
        );
    }


}
