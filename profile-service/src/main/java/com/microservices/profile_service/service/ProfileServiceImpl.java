package com.microservices.profile_service.service;

import com.microservices.profile_service.dto.ProfileRequest;
import com.microservices.profile_service.dto.ProfileResponse;
import com.microservices.profile_service.entity.Profile;
import com.microservices.profile_service.exception.ResourceAlreadyExistsException;
import com.microservices.profile_service.exception.ResourceNotFoundException;
import com.microservices.profile_service.exception.UnauthorizedException;
import com.microservices.profile_service.mapper.ProfileMapper;
import com.microservices.profile_service.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public ProfileResponse create(Long userId, ProfileRequest request) {
        if (profileRepository.existsByUserId(userId)) {
            throw new ResourceAlreadyExistsException("Profile already exists for this user");
        }

        Profile profile = profileMapper.toEntity(request);
        profile.setUserId(userId);

        return profileMapper.toResponse(profileRepository.save(profile));
    }

    @Override
    public ProfileResponse getById(Long id, Long currentUserId) {
        Profile profile = findAndCheckOwnership(id, currentUserId);
        return profileMapper.toResponse(profile);
    }

    @Override
    public ProfileResponse update(Long id, Long currentUserId, ProfileRequest request) {
        Profile profile = findAndCheckOwnership(id, currentUserId);
        profileMapper.updateEntityFromRequest(request, profile);
        return profileMapper.toResponse(profileRepository.save(profile));
    }

    @Override
    public void delete(Long id, Long currentUserId) {
        Profile profile = findAndCheckOwnership(id, currentUserId);
        profileRepository.delete(profile);
    }

    private Profile findAndCheckOwnership(Long id, Long currentUserId) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        if (!profile.getUserId().equals(currentUserId)) {
            throw new UnauthorizedException("You cannot access another user's profile");
        }

        return profile;
    }
}
