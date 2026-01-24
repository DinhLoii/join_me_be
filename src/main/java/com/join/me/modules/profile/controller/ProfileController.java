package com.join.me.modules.profile.controller;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.ApiResponse;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.profile.dto.request.UpdateProfileRequest;
import com.join.me.modules.profile.dto.response.ProfileResponse;
import com.join.me.modules.profile.service.api.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Profile Controller", description = "Endpoints for managing user profiles")
public class ProfileController {

    ProfileService profileService;

    @PatchMapping("/my-profile")
    @Operation(summary = "Update current user's profile")
    public ApiResponse<ProfileResponse> updateMyProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.updateProfile(request))
                .build();
    }

    @GetMapping("/my-profile")
    @Operation(summary = "Get current user's profile")
    public ApiResponse<ProfileResponse> getMyProfile() {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.getMyProfile())
                .build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get profile by userId (public/admin)")
    public ApiResponse<ProfileResponse> getProfile(@PathVariable String userId) {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.getProfileByUserId(userId))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all profiles with pagination (admin)")
    public ApiResponse<PagingResponse<ProfileResponse>> getAllProfiles(@Valid PagingRequest pagingRequest) {
        return ApiResponse.<PagingResponse<ProfileResponse>>builder()
                .data(profileService.getAllProfiles(pagingRequest))
                .build();
    }
}
