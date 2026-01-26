package com.join.me.modules.profile.service.impl;

import com.join.me.common.constants.GlobalVariableConstant;
import com.join.me.common.constants.JwtClaimSetConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.common.utils.PagingUtil;
import com.join.me.modules.profile.dto.request.UpdateProfileRequest;
import com.join.me.modules.profile.dto.response.ProfileResponse;
import com.join.me.modules.profile.entity.Profile;
import com.join.me.modules.profile.enums.GenderEnums;
import com.join.me.modules.profile.mapper.ProfileMapper;
import com.join.me.modules.profile.repository.ProfileRepository;
import com.join.me.modules.profile.service.api.ProfileService;
import com.join.me.modules.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository profileRepository;
    ProfileMapper profileMapper;

    @Override
    @Transactional
    public Profile createDefaultProfile(User user) {
        log.info("Creating default profile for user: {}", user.getUsername());
        Profile profile = Profile.builder()
                .userId(user.getId())
                .user(user)
                .fullName(user.getUsername())
                .gender(GenderEnums.OTHER.name())
                .build();
        return profileRepository.save(profile);
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(UpdateProfileRequest request) {
        String currentUserId = getCurrentUserId();
        Profile profile = profileRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVER_UNCATEGORIZED_EXCEPTION)); // TODO: Add
                                                                                                // PROFILE_NOT_FOUND to
                                                                                                // common.ErrorCode

        profileMapper.updateProfile(profile, request);
        return profileMapper.toProfileResponse(profileRepository.save(profile));
    }

    @Override
    public ProfileResponse getProfileByUserId(String userId) {
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVER_UNCATEGORIZED_EXCEPTION)); // TODO: Add
                                                                                                // PROFILE_NOT_FOUND to
                                                                                                // common.ErrorCode
        return profileMapper.toProfileResponse(profile);
    }

    @Override
    public PagingResponse<ProfileResponse> getAllProfiles(PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(
                Math.max(0, pagingRequest.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX),
                pagingRequest.getPageSize() <= 0 ? 10 : pagingRequest.getPageSize(),
                PagingUtil.createSort(pagingRequest));

        Page<Profile> profilePage = profileRepository.findAll(pageable);

        return PagingResponse.<ProfileResponse>builder()
                .currentPage(pagingRequest.getPage())
                .pageSize(profilePage.getSize())
                .totalPages(profilePage.getTotalPages())
                .totalElement(profilePage.getTotalElements())
                .data(
                        profilePage.getContent().stream()
                                .map(profileMapper::toProfileResponse)
                                .toList())
                .build();
    }

    @Override
    public ProfileResponse getMyProfile() {
        String currentUserId = getCurrentUserId();
        Profile profile = profileRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVER_UNCATEGORIZED_EXCEPTION)); // TODO: Add
                                                                                                // PROFILE_NOT_FOUND to
                                                                                                // common.ErrorCode
        return profileMapper.toProfileResponse(profile);
    }

    private String getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.AUTH_UNAUTHENTICATED);
        }

        if (authentication instanceof JwtAuthenticationToken jwtToken) {
            Object userIdClaim = jwtToken.getToken().getClaim(JwtClaimSetConstant.CLAIM_USER_ID);
            if (userIdClaim != null) {
                return userIdClaim.toString();
            }
        }

        return authentication.getName(); // Fallback to name (sub)
    }

}
