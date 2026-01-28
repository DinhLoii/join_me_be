package com.join.me.modules.profile.dto.response;

import com.join.me.modules.profile.enums.GenderEnums;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

/**
 * Profile response DTO
 * Used for get / update profile APIs
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileResponse {

    // =========================
    // PROFILE INFORMATION
    // =========================
    String userId;

    Instant createdAt;
    Instant updatedAt;

    String fullName;
    String avatarUrl;
    String bio;
    String address;
    String socialUrls;

    LocalDate dob;
    String relationshipStatus;
    String backgroundUrl;
    GenderEnums gender;
    String educationLevel;
    String hobbies;

    // =========================
    // USER INFORMATION
    // =========================
    String username;
    String userStatus;

    /**
     * Roles of user (e.g. USER, ADMIN)
     */
    Set<String> userRoles;
}
