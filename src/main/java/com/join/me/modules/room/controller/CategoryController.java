package com.join.me.modules.room.controller;

import com.join.me.common.constants.ApiConstant;
import com.join.me.common.constants.PaginationConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.request.SortRequest;
import com.join.me.common.dtos.response.ApiResponse;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.room.dto.request.CategoryCreationRequest;
import com.join.me.modules.room.dto.request.CategoryUpdateRequest;
import com.join.me.modules.room.dto.response.CategoryResponse;
import com.join.me.modules.room.service.api.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryCreationRequest request
    ) {
        ApiResponse<CategoryResponse> response = ApiResponse.<CategoryResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(categoryService.createCategory(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResponse<CategoryResponse>>> getAllCategories(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = PaginationConstant.DESC) String direction,
            @RequestParam(required = false, defaultValue = "createdAt") String field
    ) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .pageSize(size)
                .sortRequest(SortRequest.builder()
                        .direction(direction)
                        .field(field)
                        .build())
                .build();

        ApiResponse<PagingResponse<CategoryResponse>> response = ApiResponse.<PagingResponse<CategoryResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(categoryService.getAllCategories(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(
            @PathVariable String categoryId
    ) {
        ApiResponse<CategoryResponse> response = ApiResponse.<CategoryResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(categoryService.getCategoryById(categoryId))
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable String categoryId,
            @Valid @RequestBody CategoryUpdateRequest request
    ) {
        ApiResponse<CategoryResponse> response = ApiResponse.<CategoryResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(categoryService.updateCategory(categoryId, request))
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable String categoryId
    ) {
        categoryService.deleteCategory(categoryId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(ApiConstant.SUCCESS)
                .build();

        return ResponseEntity.ok(response);
    }
}

