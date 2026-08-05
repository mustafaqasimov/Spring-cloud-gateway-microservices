package com.microservices.profile_service.service;

import com.microservices.profile_service.dto.ProfileRequest;
import com.microservices.profile_service.dto.ProfileResponse;

public interface ProfileService {
    ProfileResponse create(Long userId, ProfileRequest request);
    ProfileResponse getById(Long id, Long currentUserId);
    ProfileResponse update(Long id, Long currentUserId, ProfileRequest request);
    void delete(Long id, Long currentUserId);
}
