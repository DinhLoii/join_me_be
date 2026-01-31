package com.join.me.modules.room.controller;

import com.join.me.common.constants.ApiConstant;
import com.join.me.common.constants.PaginationConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.request.SortRequest;
import com.join.me.common.dtos.response.ApiResponse;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.room.dto.request.RoomJoinRequestCreationRequest;
import com.join.me.modules.room.dto.response.RoomJoinRequestResponse;
import com.join.me.modules.room.service.api.RoomJoinRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomJoinRequestController {

    RoomJoinRequestService roomJoinRequestService;

    @PostMapping("/{roomId}/join-requests")
    public ResponseEntity<ApiResponse<RoomJoinRequestResponse>> createJoinRequest(
            @PathVariable String roomId
    ) {
        RoomJoinRequestCreationRequest request = RoomJoinRequestCreationRequest.builder()
                .roomId(roomId)
                .build();
        ApiResponse<RoomJoinRequestResponse> response = ApiResponse.<RoomJoinRequestResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomJoinRequestService.createJoinRequest(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/join-requests/{requestId}/accept")
    public ResponseEntity<ApiResponse<RoomJoinRequestResponse>> acceptJoinRequest(
            @PathVariable String requestId
    ) {
        ApiResponse<RoomJoinRequestResponse> response = ApiResponse.<RoomJoinRequestResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomJoinRequestService.acceptJoinRequest(requestId))
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/join-requests/{requestId}/reject")
    public ResponseEntity<ApiResponse<RoomJoinRequestResponse>> rejectJoinRequest(
            @PathVariable String requestId
    ) {
        ApiResponse<RoomJoinRequestResponse> response = ApiResponse.<RoomJoinRequestResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomJoinRequestService.rejectJoinRequest(requestId))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/join-requests")
    public ResponseEntity<ApiResponse<PagingResponse<RoomJoinRequestResponse>>> getRoomJoinRequests(
            @PathVariable String roomId,
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

        ApiResponse<PagingResponse<RoomJoinRequestResponse>> response = ApiResponse.<PagingResponse<RoomJoinRequestResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomJoinRequestService.getRoomJoinRequests(roomId, request))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/join-requests/me")
    public ResponseEntity<ApiResponse<PagingResponse<RoomJoinRequestResponse>>> getMyJoinRequests(
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

        ApiResponse<PagingResponse<RoomJoinRequestResponse>> response = ApiResponse.<PagingResponse<RoomJoinRequestResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomJoinRequestService.getMyJoinRequests(request))
                .build();

        return ResponseEntity.ok(response);
    }
}

