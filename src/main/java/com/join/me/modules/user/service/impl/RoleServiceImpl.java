package com.join.me.modules.user.service.impl;

import com.join.me.common.constants.GlobalVariableConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.common.utils.PagingUtil;
import com.join.me.modules.user.dto.request.RoleUpdateRequest;
import com.join.me.modules.user.dto.response.RoleResponse;
import com.join.me.modules.user.entity.Permission;
import com.join.me.modules.user.entity.Role;
import com.join.me.modules.user.mapper.RoleMapper;
import com.join.me.modules.user.repository.PermissionRepository;
import com.join.me.modules.user.repository.RoleRepository;
import com.join.me.modules.user.service.api.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public PagingResponse<RoleResponse> getRoles(PagingRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Page<Role> rolePage = roleRepository.findAll(pageable);

        return PagingResponse.<RoleResponse>builder()
                .currentPage(request.getPage())
                .pageSize(rolePage.getSize())
                .totalPages(rolePage.getTotalPages())
                .totalElement(rolePage.getTotalElements())
                .data(rolePage.getContent().stream()
                        .map(roleMapper::toRoleResponse)
                        .toList())
                .build();
    }

    @Override
    public RoleResponse getById(String roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        return roleMapper.toRoleResponse(role);
    }

    @Override
    @Transactional
    public RoleResponse update(String roleId, RoleUpdateRequest request) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        if (request.getRoleName() != null && !request.getRoleName().equals(role.getRoleName())) {
            validateRoleNameNotExisted(request.getRoleName(), roleId);
            role.setRoleName(request.getRoleName());
        }

        if (request.getPermissionIds() != null) {
            List<Permission> permissions = permissionRepository.findAllById(request.getPermissionIds());
            if (permissions.size() != request.getPermissionIds().size()) {
                throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
            }
            role.setPermissions(new HashSet<>(permissions));
        }

        Role updated = roleRepository.save(role);
        return roleMapper.toRoleResponse(updated);
    }

    @Override
    @Transactional
    public void deleteById(String roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        roleRepository.deleteById(roleId);
    }

    private void validateRoleNameNotExisted(String roleName, String currentRoleId) {
        roleRepository.findByRoleName(roleName)
                .ifPresent(existingRole -> {
                    if (!existingRole.getId().equals(currentRoleId)) {
                        throw new AppException(ErrorCode.ROLE_NAME_EXISTED);
                    }
                });
    }
}

