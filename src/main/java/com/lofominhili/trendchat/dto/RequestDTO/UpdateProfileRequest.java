package com.lofominhili.trendchat.dto.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProfileRequest {

    @Size(max = 40)
    private final String name;

    @Size(max = 40)
    private final String surname;

    @Size(max = 40)
    private final String username;

    @Size(max = 40)
    @Email(message = "Invalid Email pattern")
    private final String email;

}
