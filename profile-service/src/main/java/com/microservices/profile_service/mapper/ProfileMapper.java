package com.microservices.profile_service.mapper;

import com.microservices.profile_service.dto.ProfileRequest;
import com.microservices.profile_service.dto.ProfileResponse;
import com.microservices.profile_service.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile toEntity(ProfileRequest request);

    ProfileResponse toResponse(Profile profile);

    void updateEntityFromRequest(ProfileRequest request, @MappingTarget Profile profile);
}
