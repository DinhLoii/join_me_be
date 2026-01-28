package com.join.me.modules.user.service.impl;

import com.join.me.common.constants.GlobalVariableConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.common.utils.PagingUtil;
import com.join.me.modules.auth.utils.AuthUtils;
import com.join.me.modules.user.dto.request.UserCreationRequest;
import com.join.me.modules.user.dto.request.UserRolesUpdateRequest;
import com.join.me.modules.user.dto.response.UserResponse;
import com.join.me.modules.user.entity.Role;
import com.join.me.modules.user.entity.User;
import com.join.me.modules.user.enums.UserStatus;
import com.join.me.modules.user.mapper.UserMapper;
import com.join.me.modules.user.constants.role.PredefinedRole;
import com.join.me.modules.user.repository.RoleRepository;
import com.join.me.modules.user.repository.UserRepository;
import com.join.me.modules.user.service.api.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository usersRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse create(UserCreationRequest request) {
        validateUsernameExisted(request.getUsername());
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        
        Role userRole = roleRepository.findByRoleName(PredefinedRole.USER_ROLE)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRoles(new HashSet<>(Set.of(userRole)));
        
        return userMapper.toUserResponse(usersRepository.save(user));
    }

    @Override
    public PagingResponse<UserResponse> getUsers(PagingRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Page<User> userPage = usersRepository.findAll(pageable);

        return PagingResponse.<UserResponse>builder()
                .currentPage(request.getPage())
                .pageSize(userPage.getSize())
                .totalPages(userPage.getTotalPages())
                .totalElement(userPage.getTotalElements())
                .data(userPage.getContent().stream()
                        .map(userMapper::toUserResponse)
                        .toList())
                .build();
    }

    @Override
    @Transactional
    public UserResponse updateRoles(String userId, UserRolesUpdateRequest request) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTH_UNAUTHENTICATED));

        List<Role> roles = roleRepository.findAllById(request.getRoleIds());
        user.setRoles(new HashSet<>(roles));

        User updated = usersRepository.save(user);
        return userMapper.toUserResponse(updated);
    }

    @Override
    public UserResponse getMyInfo() {
        String userId = AuthUtils.getCurrentUserId();
        if (userId == null) {
            throw new AppException(ErrorCode.AUTH_UNAUTHENTICATED);
        }

        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTH_UNAUTHENTICATED));

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteById(String userId) {
        usersRepository.deleteById(userId);
    }

    private void validateUsernameExisted(String username) {
        if (usersRepository.existsByUsername(username)) {
            // TODO: bổ sung ErrorCode riêng cho user khi mở rộng ErrorCode
            throw new AppException(ErrorCode.AUTH_UNAUTHENTICATED);
        }
    }
}
