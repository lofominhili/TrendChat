package com.lofominhili.trendchat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotEmpty(message = "The 'name' must not be null")
    @Email(message = "The 'email' must be valid")
    private String email;

    @NotEmpty(message = "The 'password' must not be null")
    String password;
}
