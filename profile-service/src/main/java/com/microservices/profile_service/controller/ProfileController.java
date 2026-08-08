package com.microservices.profile_service.controller;

import com.microservices.profile_service.dto.ProfileRequest;
import com.microservices.profile_service.dto.ProfileResponse;
import com.microservices.profile_service.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "User profile CRUD operations")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "Create your profile", description = "Creates a new user profile")
    @PostMapping
    public ResponseEntity<ProfileResponse> create(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody ProfileRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.create(userId, request));
    }

    @Operation(summary = "Get a profile by id (only the owner)", description = "Retrieves a user profile by its ID if the user is the owner")
    @GetMapping("/{id}")
    public ProfileResponse getById(
            @RequestHeader("X-User-Id") Long currentUserId,
            @PathVariable Long id
    ) {
        return profileService.getById(id, currentUserId);
    }

    @Operation(summary = "Update your profile", description = "Updates an existing user profile")
    @PutMapping("/{id}")
    public ProfileResponse update(
            @RequestHeader("X-User-Id") Long currentUserId,
            @PathVariable Long id,
            @Valid @RequestBody ProfileRequest request
    ) {
        return profileService.update(id, currentUserId, request);
    }

    @Operation(summary = "Delete your profile", description = "Deletes a user profile")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-User-Id") Long currentUserId,
            @PathVariable Long id
    ) {
        profileService.delete(id, currentUserId);
        return ResponseEntity.noContent().build();
    }
}
