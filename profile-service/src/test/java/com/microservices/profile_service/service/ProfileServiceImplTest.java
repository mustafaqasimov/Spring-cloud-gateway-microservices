package com.microservices.profile_service.service;

import com.microservices.profile_service.dto.ProfileRequest;
import com.microservices.profile_service.dto.ProfileResponse;
import com.microservices.profile_service.entity.Profile;
import com.microservices.profile_service.exception.ResourceNotFoundException;
import com.microservices.profile_service.exception.UnauthorizedException;
import com.microservices.profile_service.mapper.ProfileMapper;
import com.microservices.profile_service.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void create_savesProfileWhenUserHasNoExistingProfile() {
        Long userId = 1L;
        ProfileRequest request = new ProfileRequest("Mustafa", "mustafa1@gmail.com", "Salam");
        Profile mappedProfile = new Profile();

        when(profileRepository.existsByUserId(userId)).thenReturn(false);
        when(profileMapper.toEntity(request)).thenReturn(mappedProfile);
        when(profileRepository.save(mappedProfile)).thenReturn(mappedProfile);

        profileService.create(userId, request);

        verify(profileMapper).toEntity(request);
        verify(profileRepository).existsByUserId(userId);
        verify(profileRepository).save(mappedProfile);

        // ASSERT
        verify(profileRepository).save(mappedProfile);
    }

    @Test
    void update_shouldUpdateProfile_whenUserOwnsProfile() {
        Long id = 1L;
        Long currentUserId = 10L;

        ProfileRequest request =
                new ProfileRequest("Mustafa", "mustafa@gmail.com", "new bio");

        Profile profile = new Profile();
        profile.setId(id);
        profile.setUserId(currentUserId);

        when(profileRepository.findById(id))
                .thenReturn(Optional.of(profile));

        when(profileRepository.save(profile))
                .thenReturn(profile);

        ProfileResponse response = new ProfileResponse();

        when(profileMapper.toResponse(profile))
                .thenReturn(response);

        ProfileResponse result =
                profileService.update(id, currentUserId, request);

        verify(profileMapper)
                .updateEntityFromRequest(request, profile);

        verify(profileRepository)
                .save(profile);

        assertEquals(response, result);
    }

    @Test
    void update_shouldThrowException_whenProfileNotFound() {
        when(profileRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                profileService.update(1L, 10L, new ProfileRequest()));
    }

    @Test
    void update_shouldThrowException_whenUserDoesNotOwnProfile() {
        Profile profile = new Profile();

        ProfileRequest request =
                new ProfileRequest("Mustafa123", "mustafa123@gmail.com", "new bio");

        profile.setUserId(20L);
        when(profileRepository.findById(1L))
                .thenReturn(Optional.of(profile));
        assertThrows(
                UnauthorizedException.class,
                () -> profileService.update(1L, 10L, request)
        );
        verify(profileRepository, never())
                .save(any());
    }

    @Test
    void delete_shouldDeleteProfile_whenUserOwnsProfile() {
        Long id = 1L;
        Profile profile = new Profile();
        profile.setId(id);
        profile.setUserId(10L);

        when(profileRepository.findById(id))
                .thenReturn(Optional.of(profile));

        profileService.delete(id, 10L);

        verify(profileRepository).delete(profile);
    }

    @Test
    void delete_shouldThrowException_whenProfileNotFound() {
        when(profileRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                profileService.delete(1L, 10L));
    }

    @Test
    void delete_shouldThrowException_whenUserDoesNotOwnProfile() {
        Profile profile = new Profile();
        profile.setUserId(20L);

        when(profileRepository.findById(1L))
                .thenReturn(Optional.of(profile));

        assertThrows(UnauthorizedException.class, () ->
                profileService.delete(1L, 10L));

        verify(profileRepository, never())
                .delete(any());
    }

    @Test
    void getById() {
        Long id = 1L;
        Long currentUserId = 10L;
        Profile profile = new Profile();
        profile.setId(id);
        profile.setUserId(currentUserId);

        when(profileRepository.findById(id))
                .thenReturn(Optional.of(profile));

        ProfileResponse response = new ProfileResponse();

        when(profileMapper.toResponse(profile))
                .thenReturn(response);

        ProfileResponse result = profileService.getById(id, currentUserId);

        verify(profileRepository).findById(id);
        assertEquals(response, result);
    }
}
