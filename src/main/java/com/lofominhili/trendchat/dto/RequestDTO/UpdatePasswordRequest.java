package com.lofominhili.trendchat.dto.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePasswordRequest {

    @NotNull
    @NotBlank(message = "The 'old password' must not be null")
    @JsonProperty(value = "old_password")
    private final String oldPassword;

    @NotNull
    @NotBlank(message = "The 'new password' must not be null")
    @JsonProperty(value = "new_password")
    private final String newPassword;


}
