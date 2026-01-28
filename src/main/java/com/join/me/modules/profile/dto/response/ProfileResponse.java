package com.join.me.modules.profile.dto.response;

import com.join.me.modules.profile.enums.GenderEnums;
import com.join.me.modules.profile.enums.RelationshipStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileResponse {
    String userId;
    Instant createdAt;
    Instant updatedAt;
    String fullName;
    String email;
    String avatarUrl;
    String bio;
    AddressResponse address;
    List<PlatformLinkResponse> socialUrls;
    LocalDate dob;
    RelationshipStatus relationshipStatus;
    String backgroundUrl;
    GenderEnums gender;
    UniversityResponse university;
    List<String> hobbies;
    String username;
    String userStatus;
    Set<String> userRoles;
}
