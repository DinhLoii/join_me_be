package com.join.me.modules.profile.dto.request;

import com.join.me.modules.profile.constant.profile.ProfileConstants;
import com.join.me.modules.profile.constant.profile.ProfileErrorCodeConstants;
import com.join.me.modules.profile.enums.GenderEnums;
import com.join.me.modules.profile.enums.RelationshipStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileRequest {

        @NotBlank(message = ProfileErrorCodeConstants.PROFILE_FULL_NAME_NOT_BLANK)
        @Size(min = ProfileConstants.FULL_NAME_MIN,
                max = ProfileConstants.FULL_NAME_MAX,
                message = ProfileErrorCodeConstants.PROFILE_FULL_NAME_SIZE_INVALID)
        String fullName;

        @Size(max = ProfileConstants.AVATAR_URL_MAX_LENGTH,
                message = ProfileErrorCodeConstants.PROFILE_UPDATE_FAILED)
        String avatarUrl;

        @Size(max = ProfileConstants.BIO_MAX_LENGTH,
                message = ProfileErrorCodeConstants.PROFILE_BIO_SIZE_INVALID)
        String bio;

        @Valid
        UpdateAddressRequest address;

        @Valid
        List<UpdatePlatformLinkRequest> socialUrls;

        LocalDate dob;

        RelationshipStatus relationshipStatus;

        @Size(max = ProfileConstants.BACKGROUND_URL_MAX_LENGTH,
                message = ProfileErrorCodeConstants.PROFILE_BACKGROUND_URL_SIZE_INVALID)
        String backgroundUrl;

        @NotNull(message = ProfileErrorCodeConstants.PROFILE_GENDER_INVALID)
        GenderEnums gender;

        String universityId;

        List<String> hobbies;
}
