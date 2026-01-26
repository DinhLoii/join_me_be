package com.join.me.modules.profile.dto.request;

import com.join.me.modules.profile.constant.profile.ProfileConstants;
import com.join.me.modules.profile.constant.profile.ProfileErrorCodeConstants;
import com.join.me.modules.profile.enums.GenderEnums;
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

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileRequest {

        @NotBlank(message = ProfileErrorCodeConstants.PROFILE_FULL_NAME_NOT_BLANK)
        @Size(min = ProfileConstants.FULL_NAME_MIN, max = ProfileConstants.FULL_NAME_MAX, message = ProfileErrorCodeConstants.PROFILE_FULL_NAME_SIZE_INVALID)
        String fullName;

        @Size(max = ProfileConstants.AVATAR_URL_MAX_LENGTH, message = ProfileErrorCodeConstants.PROFILE_UPDATE_FAILED)
        String avatarUrl;

        @Size(max = ProfileConstants.BIO_MAX_LENGTH, message = ProfileErrorCodeConstants.PROFILE_BIO_SIZE_INVALID)
        String bio;

        @Size(max = ProfileConstants.ADDRESS_MAX_LENGTH, message = ProfileErrorCodeConstants.PROFILE_ADDRESS_SIZE_INVALID)
        String address;

        /**
         * social_url[] theo ERD
         * Lưu dạng TEXT / JSON string
         */
        @Size(max = ProfileConstants.SOCIAL_URLS_MAX_LENGTH, message = ProfileErrorCodeConstants.PROFILE_SOCIAL_URL_INVALID)
        String socialUrls;

        LocalDate dob;

        @Size(max = ProfileConstants.RELATIONSHIP_STATUS_MAX_LENGTH, message = ProfileErrorCodeConstants.PROFILE_RELATIONSHIP_STATUS_INVALID)
        String relationshipStatus;

        @Size(max = ProfileConstants.BACKGROUND_URL_MAX_LENGTH, message = ProfileErrorCodeConstants.PROFILE_BACKGROUND_URL_SIZE_INVALID)
        String backgroundUrl;

        @NotNull(message = ProfileErrorCodeConstants.PROFILE_GENDER_INVALID)
        GenderEnums gender;
}
