package com.join.me.modules.user.mapper;

import com.join.me.modules.user.dto.response.PermissionResponse;
import com.join.me.modules.user.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);
}
