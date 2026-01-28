package com.join.me.modules.profile.mapper;

import com.join.me.modules.profile.dto.request.UpdateProfileRequest;
import com.join.me.modules.profile.dto.response.ProfileResponse;
import com.join.me.modules.profile.entity.Profile;
import com.join.me.modules.user.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userRoles", expression = "java(mapRoles(profile.getUser().getRoles()))")
    @Mapping(target = "userStatus", ignore = true) // status not yet implemented in User entity
    ProfileResponse toProfileResponse(Profile profile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateProfile(@MappingTarget Profile profile, UpdateProfileRequest request);

    default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null)
            return null;
        return roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());
    }
}
