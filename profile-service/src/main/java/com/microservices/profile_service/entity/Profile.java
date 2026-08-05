package com.microservices.profile_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Profile extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "bio", length = 500)
    String bio;
}
