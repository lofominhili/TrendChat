package com.lofominhili.trendchat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @Size(max = 40)
    @NotBlank(message = "The 'name' must not be null")
    private final String name;

    @Size(max = 40)
    @NotBlank(message = "The 'surname' must not be null")
    private final String surname;

    @NotNull
    @NotBlank(message = "The 'username' must not be null")
    private final String username;

    @NotNull
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String password;

    @Size(max = 40)
    @NotBlank(message = "The 'email' must not be null")
    @Email(message = "Invalid Email pattern")
    private final String email;

    public UserDTO(Long id, String name, String surname, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
