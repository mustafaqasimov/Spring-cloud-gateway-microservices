package com.microservices.profile_service.repository;

import com.microservices.profile_service.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {


}
