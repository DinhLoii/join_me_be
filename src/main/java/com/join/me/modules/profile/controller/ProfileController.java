package com.join.me.modules.profile.controller;

import com.join.me.common.constants.ApiConstant;
import com.join.me.common.constants.PaginationConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.request.SortRequest;
import com.join.me.common.dtos.response.ApiResponse;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.profile.constant.profile.ProfileControllerConstants;
import com.join.me.modules.profile.dto.request.UpdateProfileRequest;
import com.join.me.modules.profile.dto.response.ProfileResponse;
import com.join.me.modules.profile.service.api.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Profile Management", description = "Profile management API")
@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {

        ProfileService profileService;

        @GetMapping
        @Operation(summary = "Get all profiles", description = "Get list of all profiles with pagination")
        public ResponseEntity<ApiResponse<PagingResponse<ProfileResponse>>> getAllProfiles(
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                        @RequestParam(required = false, defaultValue = "createdAt") String field,
                        @RequestParam(required = false, defaultValue = PaginationConstant.DESC) String direction) {
                PagingRequest pagingRequest = PagingRequest.builder()
                                .page(page)
                                .pageSize(size)
                                .sortRequest(new SortRequest(direction, field))
                                .build();

                ApiResponse<PagingResponse<ProfileResponse>> response = ApiResponse
                                .<PagingResponse<ProfileResponse>>builder()
                                .success(ApiConstant.SUCCESS)
                                .data(profileService.getAllProfiles(pagingRequest))
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping("/me")
        @Operation(summary = "Get my profile", description = "Get current user's profile information")
        public ResponseEntity<ApiResponse<ProfileResponse>> getMyProfile() {
                ApiResponse<ProfileResponse> response = ApiResponse
                                .<ProfileResponse>builder()
                                .success(ApiConstant.SUCCESS)
                                .data(profileService.getMyProfile())
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping("/{userId}")
        @Operation(summary = "Get profile by ID", description = "Get profile details by user ID")
        public ResponseEntity<ApiResponse<ProfileResponse>> getProfileById(
                        @PathVariable String userId) {
                ApiResponse<ProfileResponse> response = ApiResponse
                                .<ProfileResponse>builder()
                                .success(ApiConstant.SUCCESS)
                                .data(profileService.getProfileByUserId(userId))
                                .build();

                return ResponseEntity.ok(response);
        }

        @PutMapping("/me")
        @Operation(summary = "Update profile", description = "Update current user's profile information")
        public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
                        @Valid @RequestBody UpdateProfileRequest request) {
                log.info("update my profile");
                ApiResponse<ProfileResponse> response = ApiResponse
                                .<ProfileResponse>builder()
                                .success(ApiConstant.SUCCESS)
                                .data(profileService.updateProfile(request))
                                .build();

                return ResponseEntity.ok(response);
        }
}
