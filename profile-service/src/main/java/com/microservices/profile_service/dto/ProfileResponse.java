package com.microservices.profile_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Profile response")
public class ProfileResponse {
    @Schema(description = "Profile ID")
    Long id;
    @Schema(description = "User ID")
    Long userId;
    @Schema(description = "Profile name")
    String name;
    @Schema(description = "Profile email")
    String email;
    @Schema(description = "Profile bio")
    String bio;
}
