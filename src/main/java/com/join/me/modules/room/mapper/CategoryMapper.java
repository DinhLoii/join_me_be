package com.join.me.modules.room.mapper;

import com.join.me.modules.room.dto.request.CategoryCreationRequest;
import com.join.me.modules.room.dto.request.CategoryUpdateRequest;
import com.join.me.modules.room.dto.response.CategoryResponse;
import com.join.me.modules.room.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryCreationRequest request);

    @Mapping(target = "rooms", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);

    CategoryResponse toCategoryResponse(Category category);
}

