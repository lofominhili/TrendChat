package com.lofominhili.trendchat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(max = 40)
    @NotBlank(message = "The 'name' must not be null")
    private String name;

    @Size(max = 40)
    @NotBlank(message = "The 'surname' must not be null")
    private String surname;

    @Size(min = 3, max = 20)
    @NotNull
    @NotBlank(message = "The 'username' must not be null")
    private String username;

    @Size(min = 6, max = 40)
    @NotNull
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Size(max = 40)
    @NotBlank(message = "The 'email' must not be null")
    @Email(message = "Invalid Email pattern")
    private String email;

}
