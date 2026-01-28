package com.join.me.modules.user.service.api;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.user.dto.request.UserCreationRequest;
import com.join.me.modules.user.dto.request.UserRolesUpdateRequest;
import com.join.me.modules.user.dto.response.UserResponse;

public interface UserService {

    UserResponse create(UserCreationRequest request);

    PagingResponse<UserResponse> getUsers(PagingRequest request);

    UserResponse updateRoles(String userId, UserRolesUpdateRequest request);

    UserResponse getMyInfo();

    void deleteById(String userId);
}