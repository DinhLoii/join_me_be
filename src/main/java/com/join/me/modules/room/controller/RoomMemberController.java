package com.join.me.modules.room.controller;

import com.join.me.common.constants.ApiConstant;
import com.join.me.common.constants.PaginationConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.request.SortRequest;
import com.join.me.common.dtos.response.ApiResponse;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.room.dto.response.RoomMemberResponse;
import com.join.me.modules.room.service.api.RoomMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class RoomMemberController {

    RoomMemberService roomMemberService;

    @PostMapping("/{roomId}/members")
    public ResponseEntity<ApiResponse<RoomMemberResponse>> joinRoom(
            @PathVariable String roomId
    ) {
        ApiResponse<RoomMemberResponse> response = ApiResponse.<RoomMemberResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomMemberService.joinRoom(roomId))
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{roomId}/members/me")
    public ResponseEntity<ApiResponse<Void>> leaveRoom(
            @PathVariable String roomId
    ) {
        roomMemberService.leaveRoom(roomId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(ApiConstant.SUCCESS)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/members")
    public ResponseEntity<ApiResponse<PagingResponse<RoomMemberResponse>>> getRoomMembers(
            @PathVariable String roomId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = PaginationConstant.DESC) String direction,
            @RequestParam(required = false, defaultValue = "joinedAt") String field
    ) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .pageSize(size)
                .sortRequest(SortRequest.builder()
                        .direction(direction)
                        .field(field)
                        .build())
                .build();

        ApiResponse<PagingResponse<RoomMemberResponse>> response = ApiResponse.<PagingResponse<RoomMemberResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(roomMemberService.getRoomMembers(roomId, request))
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{roomId}/members/{memberId}")
    public ResponseEntity<ApiResponse<Void>> kickMember(
            @PathVariable String roomId,
            @PathVariable String memberId
    ) {
        roomMemberService.kickMember(roomId, memberId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(ApiConstant.SUCCESS)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{roomId}/members/{memberId}/block")
    public ResponseEntity<ApiResponse<Void>> blockMember(
            @PathVariable String roomId,
            @PathVariable String memberId
    ) {
        roomMemberService.blockMember(roomId, memberId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(ApiConstant.SUCCESS)
                .build();

        return ResponseEntity.ok(response);
    }
}

