package com.join.me.modules.profile.mapper;

import com.join.me.modules.profile.dto.response.PlatformLinkResponse;
import com.join.me.modules.profile.entity.PlatformLink;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlatformLinkMapper {
    PlatformLinkResponse toPlatformLinkDto(PlatformLink platformLink);
}


