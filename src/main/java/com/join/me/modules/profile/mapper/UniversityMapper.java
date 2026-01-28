package com.join.me.modules.profile.mapper;

import com.join.me.modules.profile.dto.response.UniversityResponse;
import com.join.me.modules.profile.entity.University;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityMapper {
    UniversityResponse toUniversityDto(University university);
}


