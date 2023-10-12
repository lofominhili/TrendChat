package com.lofominhili.trendchat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequest {

    @Size(max = 40)
    @Email(message = "Invalid Email pattern")
    @NotBlank(message = "The 'email' must not be null")
    private final String email;

    @NotNull
    private final String password;
}
