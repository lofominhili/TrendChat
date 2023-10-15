package com.lofominhili.trendchat.controllers;

import com.lofominhili.trendchat.dto.BasicDto.SuccessDTO;
import com.lofominhili.trendchat.dto.RequestDTO.SigninRequest;
import com.lofominhili.trendchat.dto.UserDTO;
import com.lofominhili.trendchat.exceptions.AuthenticationFailedException;
import com.lofominhili.trendchat.exceptions.RequestDataValidationFailedException;
import com.lofominhili.trendchat.services.AuthService.AuthService;
import com.lofominhili.trendchat.utils.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessDTO<String>> signUp(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult validationResult
    ) throws RequestDataValidationFailedException, AuthenticationFailedException {
        if (validationResult.hasErrors()) {
            throw new RequestDataValidationFailedException(GlobalExceptionHandler.handleValidationResults(validationResult));
        }
        authService.signUp(userDTO);
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.CREATED.value(),
                        "Sign Up",
                        "Successfully signed up!"
                ), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SuccessDTO<String>> signIn(
            @Valid @RequestBody SigninRequest request,
            BindingResult validationResult
    ) throws RequestDataValidationFailedException, AuthenticationFailedException {
        if (validationResult.hasErrors()) {
            throw new RequestDataValidationFailedException(GlobalExceptionHandler.handleValidationResults(validationResult));
        }
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Sign In",
                        String.format("Successfully signed in! Use this token for further operations: %s", authService.signIn(request))
                ), HttpStatus.OK);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<SuccessDTO<String>> signOut(HttpServletRequest request) {
        authService.signOut(request);
        return new ResponseEntity<>(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Sign Out",
                        "Successfully signed out!"
                ), HttpStatus.OK);
    }

}
