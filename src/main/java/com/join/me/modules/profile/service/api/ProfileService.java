package com.join.me.modules.profile.service.api;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.profile.dto.request.UpdateProfileRequest;
import com.join.me.modules.profile.dto.response.ProfileResponse;
import com.join.me.modules.user.entity.User;

public interface ProfileService {

    /**
     * Create default profile when user is registered
     * Profile uses shared primary key with User
     *
     * @author join.me
     */
    void createDefaultProfile(User user);

    /**
     * Update current user's profile
     *
     * @author join.me
     */
    ProfileResponse updateProfile(UpdateProfileRequest request);

    /**
     * Get profile detail by userId
     * (public / admin usage)
     *
     * @author join.me
     */
    ProfileResponse getProfileByUserId(String userId);

    /**
     * Get all profiles with pagination
     * (admin usage)
     *
     * @param pagingRequest PagingRequest
     * @author join.me
     */
    PagingResponse<ProfileResponse> getAllProfiles(PagingRequest pagingRequest);

    /**
     * Get current user's profile
     *
     * @author join.me
     */
    ProfileResponse getMyProfile();
}
