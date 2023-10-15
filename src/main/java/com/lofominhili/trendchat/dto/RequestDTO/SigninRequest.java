package com.lofominhili.trendchat.dto.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninRequest {

    @Size(max = 40)
    @Email(message = "Invalid Email pattern")
    @NotBlank(message = "The 'email' must not be null")
    private final String email;

    @NotNull
    @NotBlank(message = "The 'password' must not be null")
    private final String password;
}
