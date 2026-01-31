package com.join.me.modules.room.service.api;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.room.dto.request.CategoryCreationRequest;
import com.join.me.modules.room.dto.request.CategoryUpdateRequest;
import com.join.me.modules.room.dto.response.CategoryResponse;

public interface CategoryService {

    CategoryResponse createCategory(CategoryCreationRequest request);

    CategoryResponse updateCategory(String categoryId, CategoryUpdateRequest request);

    PagingResponse<CategoryResponse> getAllCategories(PagingRequest request);

    CategoryResponse getCategoryById(String categoryId);

    void deleteCategory(String categoryId);
}

