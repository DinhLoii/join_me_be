package com.join.me.modules.profile.controller;

import com.join.me.common.constants.ApiConstant;
import com.join.me.common.constants.PaginationConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.request.SortRequest;
import com.join.me.common.dtos.response.ApiResponse;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.profile.dto.request.UniversityCreationRequest;
import com.join.me.modules.profile.dto.request.UniversityUpdateRequest;
import com.join.me.modules.profile.dto.response.UniversityResponse;
import com.join.me.modules.profile.service.api.UniversityService;
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
@RequestMapping("/api/v1/universities")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UniversityController {

    UniversityService universityService;

    @PostMapping
    public ResponseEntity<ApiResponse<UniversityResponse>> createUniversity(@Valid @RequestBody UniversityCreationRequest request) {
        ApiResponse<UniversityResponse> response = ApiResponse.<UniversityResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(universityService.create(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResponse<UniversityResponse>>> getUniversities(
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

        ApiResponse<PagingResponse<UniversityResponse>> response = ApiResponse.<PagingResponse<UniversityResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(universityService.getUniversities(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{universityId}")
    public ResponseEntity<ApiResponse<UniversityResponse>> getUniversityById(@PathVariable String universityId) {
        ApiResponse<UniversityResponse> response = ApiResponse.<UniversityResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(universityService.getById(universityId))
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{universityId}")
    public ResponseEntity<ApiResponse<UniversityResponse>> updateUniversity(
            @PathVariable String universityId,
            @Valid @RequestBody UniversityUpdateRequest request
    ) {
        ApiResponse<UniversityResponse> response = ApiResponse.<UniversityResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(universityService.update(universityId, request))
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{universityId}")
    public ResponseEntity<ApiResponse<Void>> deleteUniversity(@PathVariable String universityId) {
        universityService.deleteById(universityId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(ApiConstant.SUCCESS)
                .build();

        return ResponseEntity.ok(response);
    }
}


