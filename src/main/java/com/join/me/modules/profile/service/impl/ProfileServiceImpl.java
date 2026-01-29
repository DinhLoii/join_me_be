package com.join.me.modules.profile.service.impl;

import com.join.me.common.constants.GlobalVariableConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.common.utils.PagingUtil;
import com.join.me.modules.auth.utils.AuthUtils;
import com.join.me.modules.profile.dto.request.UpdateAddressRequest;
import com.join.me.modules.profile.dto.request.UpdatePlatformLinkRequest;
import com.join.me.modules.profile.dto.request.UpdateProfileRequest;
import com.join.me.modules.profile.dto.response.ProfileResponse;
import com.join.me.modules.profile.entity.Address;
import com.join.me.modules.profile.entity.PlatformLink;
import com.join.me.modules.profile.entity.Profile;
import com.join.me.modules.profile.entity.University;
import com.join.me.modules.profile.enums.GenderEnums;
import com.join.me.modules.profile.mapper.ProfileMapper;
import com.join.me.modules.profile.repository.AddressRepository;
import com.join.me.modules.profile.repository.ProfileRepository;
import com.join.me.modules.profile.repository.UniversityRepository;
import com.join.me.modules.profile.service.api.ProfileService;
import com.join.me.modules.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository profileRepository;
    ProfileMapper profileMapper;
    AddressRepository addressRepository;
    UniversityRepository universityRepository;

    @Override
    @Transactional
    public void createDefaultProfile(User user) {
        profileRepository.save(Profile.builder()
                .user(user)
                .fullName(user.getUsername())
                .gender(GenderEnums.OTHER)
                .build());
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(UpdateProfileRequest request) {
        String currentUserId = AuthUtils.getCurrentUserId();

        Profile profile = profileRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));

        // Update primitive/simple fields via mapper
        profileMapper.updateProfile(profile, request);

        // Update complex relations
        updateAddress(profile, request.getAddress());
        updateSocialUrls(profile, request.getSocialUrls());
        updateUniversity(profile, request.getUniversityId());
        updateHobbies(profile, request.getHobbies());

        Profile saved = profileRepository.save(profile);
        return profileMapper.toProfileResponse(saved);
    }

    @Override
    public ProfileResponse getProfileByUserId(String userId) {
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return profileMapper.toProfileResponse(profile);
    }

    @Override
    public PagingResponse<ProfileResponse> getAllProfiles(PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(
                pagingRequest.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                pagingRequest.getPageSize(),
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
        String currentUserId = AuthUtils.getCurrentUserId();
        Profile profile = profileRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));

        return profileMapper.toProfileResponse(profile);
    }

    private void updateAddress(Profile profile, UpdateAddressRequest addressRequest) {
        if (addressRequest == null) {
            return;
        }

        Address currentAddress = profile.getAddress();
        if (currentAddress == null) {
            Address newAddress = Address.builder()
                    .province(addressRequest.getProvince())
                    .commune(addressRequest.getCommune())
                    .profile(profile)
                    .build();

            Address savedAddress = addressRepository.save(newAddress);
            profile.setAddress(savedAddress);
        } else {
            currentAddress.setProvince(addressRequest.getProvince());
            currentAddress.setCommune(addressRequest.getCommune());
        }
    }

    private void updateSocialUrls(Profile profile, List<UpdatePlatformLinkRequest> socialUrls) {
        if (socialUrls == null) {
            return;
        }

        if (profile.getSocialUrls() == null) {
            profile.setSocialUrls(new ArrayList<>());
        }

        List<PlatformLink> links = profile.getSocialUrls();
        links.clear();

        for (UpdatePlatformLinkRequest dto : socialUrls) {
            PlatformLink link = PlatformLink.builder()
                    .platform(dto.getPlatform())
                    .link(dto.getLink())
                    .profile(profile)
                    .build();
            links.add(link);
        }
    }

    private void updateUniversity(Profile profile, String universityId) {
        if (universityId == null) {
            return;
        }
        if (universityId.isBlank()) {
            profile.setUniversity(null);
            return;
        }
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new AppException(ErrorCode.UNIVERSITY_NOT_FOUND));
        profile.setUniversity(university);
    }

    private void updateHobbies(Profile profile, List<String> hobbies) {
        if (hobbies == null) {
            return;
        }
        profile.setHobbies(new ArrayList<>(hobbies));
    }

}
