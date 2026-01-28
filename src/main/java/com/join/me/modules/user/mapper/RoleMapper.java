package com.join.me.modules.user.mapper;

import com.join.me.modules.user.dto.request.RoleCreationRequest;
import com.join.me.modules.user.dto.response.RoleResponse;
import com.join.me.modules.user.entity.Permission;
import com.join.me.modules.user.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    Role toRole(RoleCreationRequest req);
    
    @Mapping(target = "permissions", source = "role.permissions")
    RoleResponse toRoleResponse(Role role);
    
    default Set<String> mapPermissions(Set<Permission> permissions) {
        if (permissions == null) {
            return null;
        }
        return permissions.stream()
                .map(Permission::getPermissionName)
                .collect(Collectors.toSet());
    }
}
