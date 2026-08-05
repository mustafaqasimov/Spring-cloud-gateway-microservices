package com.projects.auth_service.entity;

import com.projects.auth_service.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{

    @Column(name = "user_Name",nullable = false,unique = true)
    String userName;

    @Column(name = "email",nullable = false,unique = true)
    String email;

    @Column(name = "password",nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    Role role;
}