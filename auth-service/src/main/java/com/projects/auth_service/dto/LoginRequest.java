package com.projects.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Login request")
public class LoginRequest {

    @Schema(description = "Username")
    @NotBlank(message = "Username is required")
    String username;

    @Schema(description = "Password")
    @NotBlank(message = "Password is required")
    String password;
}
