package com.lofominhili.trendchat.controllers;

import com.lofominhili.trendchat.dto.BasicDto.SuccessDTO;
import com.lofominhili.trendchat.exceptions.TokenValidationException;
import com.lofominhili.trendchat.services.ProfileService.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
