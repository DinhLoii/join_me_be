package com.join.me.modules.user.mapper;

import com.join.me.modules.user.dto.request.RoleCreationRequest;
import com.join.me.modules.user.dto.response.RoleResponse;
import com.join.me.modules.user.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleCreationRequest req);
    RoleResponse toRoleResponse(Role role);
}
