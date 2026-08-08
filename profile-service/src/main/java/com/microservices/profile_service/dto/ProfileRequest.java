package com.microservices.profile_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Profile request")
public class ProfileRequest {

    @Schema(description = "Profile name")
    @NotBlank(message = "Name is required")
    @Size(max = 150, message = "Name must be at most 150 characters")
    String name;

    @Schema(description = "Profile email")
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email;

    @Schema(description = "Profile bio")
    @Size(max = 500, message = "Bio must be at most 500 characters")
    String bio;
}
