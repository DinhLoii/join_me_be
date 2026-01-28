package com.join.me.modules.user.service.api;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.user.dto.request.RoleUpdateRequest;
import com.join.me.modules.user.dto.response.RoleResponse;

public interface RoleService {

    PagingResponse<RoleResponse> getRoles(PagingRequest request);

    RoleResponse getById(String roleId);

    RoleResponse update(String roleId, RoleUpdateRequest request);

    void deleteById(String roleId);
}

