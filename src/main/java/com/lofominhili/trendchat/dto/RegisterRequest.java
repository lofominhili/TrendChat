package com.lofominhili.trendchat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "The 'name' must not be null")
    String name;

    @NotEmpty(message = "The 'surname' must not be null")
    String surname;

    @NotEmpty(message = "The 'username' must not be null")
    String username;

    @NotEmpty(message = "The 'password' must not be null")
    String password;

    @NotEmpty(message = "The 'email' must not be null")
    @Email(message = "The 'email' must be valid")
    String email;

}