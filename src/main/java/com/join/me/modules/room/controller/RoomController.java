package com.join.me.modules.room.controller;

import com.join.me.common.constants.ApiConstant;
import com.join.me.common.constants.PaginationConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.request.SortRequest;
import com.join.me.common.dtos.response.ApiResponse;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.room.dto.request.RoomCreationRequest;
import com.join.me.modules.room.dto.request.RoomUpdateRequest;
import com.join.me.modules.room.dto.response.RoomResponse;
import com.join.me.modules.room.service.api.RoomService;
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
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {

    RoomService roomService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(
            @Valid @RequestBody RoomCreationRequest request
    ) {
        ApiResponse<RoomResponse> response = ApiResponse.<RoomResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomService.createRoom(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResponse<RoomResponse>>> getAllOpenRooms(
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

        ApiResponse<PagingResponse<RoomResponse>> response = ApiResponse.<PagingResponse<RoomResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomService.getAllOpenRooms(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponse>> getRoomById(
            @PathVariable String roomId
    ) {
        ApiResponse<RoomResponse> response = ApiResponse.<RoomResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomService.getRoomById(roomId))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<PagingResponse<RoomResponse>>> getMyRooms(
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

        ApiResponse<PagingResponse<RoomResponse>> response = ApiResponse.<PagingResponse<RoomResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomService.getMyRooms(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponse>> updateMyRoom(
            @PathVariable String roomId,
            @Valid @RequestBody RoomUpdateRequest request
    ) {
        ApiResponse<RoomResponse> response = ApiResponse.<RoomResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomService.updateMyRoom(roomId, request))
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/{roomId}/close")
    public ResponseEntity<ApiResponse<RoomResponse>> closeMyRoom(
            @PathVariable String roomId
    ) {
        ApiResponse<RoomResponse> response = ApiResponse.<RoomResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomService.closeMyRoom(roomId))
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me/{roomId}")
    public ResponseEntity<ApiResponse<Void>> deleteMyRoom(
            @PathVariable String roomId
    ) {
        roomService.deleteMyRoom(roomId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(ApiConstant.SUCCESS)
                .build();

        return ResponseEntity.ok(response);
    }
}

