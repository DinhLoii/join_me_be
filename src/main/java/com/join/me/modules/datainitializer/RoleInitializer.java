package com.join.me.modules.datainitializer;

import com.join.me.common.constants.InitializerOrder;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.modules.user.constants.permission.StartDefinedPermission;
import com.join.me.modules.user.constants.role.PredefinedRole;
import com.join.me.modules.user.entity.Permission;
import com.join.me.modules.user.entity.Role;
import com.join.me.modules.user.repository.PermissionRepository;
import com.join.me.modules.user.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Order(InitializerOrder.ROLE)
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleInitializer implements ApplicationRunner {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!roleRepository.existsByRoleName(PredefinedRole.ADMIN_ROLE)) {
            Role adminRole = Role.builder()
                    .roleName(PredefinedRole.ADMIN_ROLE)
                    .permissions(getAdminPermissions())
                    .build();
            roleRepository.save(adminRole);
        }

        if (!roleRepository.existsByRoleName(PredefinedRole.USER_ROLE)) {
            Role staffRole = Role.builder()
                    .roleName(PredefinedRole.USER_ROLE)
                    .permissions(null)
                    .build();
            roleRepository.save(staffRole);
        }
    }

    private Set<Permission> getAdminPermissions() {
        Set<Permission> permissions = new HashSet<>();

        // System permissions
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.SYSTEM_MANAGE)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.SYSTEM_VIEW)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));

        // Role permissions
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.ROLE_MANAGE)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.ROLE_VIEW)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));

        // Permission permissions
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.PERMISSION_MANAGE)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.PERMISSION_VIEW)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));

        // Audit permission
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.AUDIT_VIEW)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));

        // User permissions
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.USER_VIEW)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.USER_UPDATE)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));
        permissions.add(permissionRepository.findByPermissionName(StartDefinedPermission.USER_DELETE)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));

        return permissions;
    }
}
