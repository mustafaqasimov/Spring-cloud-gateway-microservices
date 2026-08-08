package com.projects.auth_service.dto;

import com.projects.auth_service.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Authentication response")
public class AuthResponse {
    @Schema(description = "JWT token")
    String token;
    @Schema(description = "User ID")
    Long userId;
    @Schema(description = "Username")
    String username;
    @Schema(description = "User role")
    Role role;
}
