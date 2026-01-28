package com.join.me.modules.user.mapper;

import com.join.me.modules.user.dto.request.UserCreationRequest;
import com.join.me.modules.user.dto.response.UserResponse;
import com.join.me.modules.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
}
