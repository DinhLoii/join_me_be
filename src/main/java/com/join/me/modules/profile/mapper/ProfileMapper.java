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

@Mapper(componentModel = "spring", uses = {AddressMapper.class, PlatformLinkMapper.class, UniversityMapper.class})
public interface ProfileMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userRoles", source = "user.roles")
    @Mapping(target = "userStatus", source = "user.status")
    @Mapping(target = "email", source = "user.email")
    ProfileResponse toProfileResponse(Profile profile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "socialUrls", ignore = true)
    @Mapping(target = "university", ignore = true)
    @Mapping(target = "hobbies", ignore = true)
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
