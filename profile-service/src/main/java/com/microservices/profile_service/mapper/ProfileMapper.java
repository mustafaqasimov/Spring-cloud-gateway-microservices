package com.microservices.profile_service.mapper;

import com.microservices.profile_service.dto.ProfileRequest;
import com.microservices.profile_service.dto.ProfileResponse;
import com.microservices.profile_service.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile toEntity(ProfileRequest profileRequest);

    @Mapping(target = "userId", source = "profile.userId")
    @Mapping(target = "bio", source = "profile.bio")
    @Mapping(target = "name", source = "user.userName")
    @Mapping(target = "email", source = "user.email")
    ProfileResponse toResponse(Profile profile);
}
