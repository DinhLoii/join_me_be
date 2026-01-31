package com.join.me.modules.room.service.impl;

import com.join.me.common.constants.GlobalVariableConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.common.utils.PagingUtil;
import com.join.me.modules.auth.utils.AuthUtils;
import com.join.me.modules.room.dto.request.RoomJoinRequestCreationRequest;
import com.join.me.modules.room.dto.response.RoomJoinRequestResponse;
import com.join.me.modules.room.entity.Room;
import com.join.me.modules.room.entity.RoomJoinRequest;
import com.join.me.modules.room.entity.RoomMember;
import com.join.me.modules.room.enums.JoinMode;
import com.join.me.modules.room.enums.MemberRole;
import com.join.me.modules.room.enums.MemberStatus;
import com.join.me.modules.room.enums.RequestStatus;
import com.join.me.modules.room.enums.RoomStatus;
import com.join.me.modules.room.mapper.RoomJoinRequestMapper;
import com.join.me.modules.room.repository.RoomJoinRequestRepository;
import com.join.me.modules.room.repository.RoomMemberRepository;
import com.join.me.modules.room.repository.RoomRepository;
import com.join.me.modules.room.service.api.RoomJoinRequestService;
import com.join.me.modules.user.entity.User;
import com.join.me.modules.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoomJoinRequestServiceImpl implements RoomJoinRequestService {

    RoomJoinRequestRepository roomJoinRequestRepository;
    RoomRepository roomRepository;
    RoomJoinRequestMapper roomJoinRequestMapper;
    UserRepository userRepository;
    RoomMemberRepository roomMemberRepository;

    @Override
    @Transactional
    public RoomJoinRequestResponse createJoinRequest(RoomJoinRequestCreationRequest request) {
        String currentUserId = AuthUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_USERNAME_NOT_FOUND));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        if (room.getStatus() != RoomStatus.OPEN) {
            throw new AppException(ErrorCode.ROOM_EXPIRED);
        }

        if (room.getExpiresAt() != null && room.getExpiresAt().isBefore(Instant.now())) {
            throw new AppException(ErrorCode.ROOM_EXPIRED);
        }

        if (room.getJoinMode() != JoinMode.REQUEST) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        if (roomMemberRepository.existsByRoomIdAndUserId(request.getRoomId(), currentUserId)) {
            throw new AppException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }

        if (roomJoinRequestRepository.existsByRoomIdAndUserIdAndStatus(
                request.getRoomId(), currentUserId, RequestStatus.PENDING)) {
            throw new AppException(ErrorCode.REQUEST_ALREADY_EXISTS);
        }

        RoomJoinRequest joinRequest = roomJoinRequestMapper.toRoomJoinRequest(request);
        joinRequest.setRoom(room);
        joinRequest.setUser(user);
        joinRequest.setStatus(RequestStatus.PENDING);

        RoomJoinRequest saved = roomJoinRequestRepository.save(joinRequest);
        return roomJoinRequestMapper.toRoomJoinRequestResponse(saved);
    }

    @Override
    @Transactional
    public RoomJoinRequestResponse acceptJoinRequest(String requestId) {
        String currentUserId = AuthUtils.getCurrentUserId();

        RoomJoinRequest request = roomJoinRequestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new AppException(ErrorCode.REQUEST_ALREADY_PROCESSED);
        }

        Room room = request.getRoom();
        if (!room.getOwner().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        long activeMemberCount = roomMemberRepository.countByRoomIdAndStatus(room.getId(), MemberStatus.JOINED);
        if (activeMemberCount >= room.getMaxSlot()) {
            throw new AppException(ErrorCode.ROOM_FULL);
        }

        RoomMember member = RoomMember.builder()
                .room(room)
                .user(request.getUser())
                .role(MemberRole.MEMBER)
                .status(MemberStatus.JOINED)
                .joinedAt(Instant.now())
                .build();
        roomMemberRepository.save(member);

        request.setStatus(RequestStatus.ACCEPTED);
        RoomJoinRequest saved = roomJoinRequestRepository.save(request);

        return roomJoinRequestMapper.toRoomJoinRequestResponse(saved);
    }

    @Override
    @Transactional
    public RoomJoinRequestResponse rejectJoinRequest(String requestId) {
        String currentUserId = AuthUtils.getCurrentUserId();

        RoomJoinRequest request = roomJoinRequestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new AppException(ErrorCode.REQUEST_ALREADY_PROCESSED);
        }

        Room room = request.getRoom();
        if (!room.getOwner().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        request.setStatus(RequestStatus.REJECTED);
        RoomJoinRequest saved = roomJoinRequestRepository.save(request);

        return roomJoinRequestMapper.toRoomJoinRequestResponse(saved);
    }

    @Override
    public PagingResponse<RoomJoinRequestResponse> getRoomJoinRequests(String roomId, PagingRequest request) {
        String currentUserId = AuthUtils.getCurrentUserId();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        if (!room.getOwner().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Page<RoomJoinRequest> requestPage = roomJoinRequestRepository.findByRoomId(roomId, pageable);

        return PagingResponse.<RoomJoinRequestResponse>builder()
                .currentPage(request.getPage())
                .pageSize(requestPage.getSize())
                .totalPages(requestPage.getTotalPages())
                .totalElement(requestPage.getTotalElements())
                .data(requestPage.getContent().stream()
                        .map(roomJoinRequestMapper::toRoomJoinRequestResponse)
                        .toList())
                .build();
    }

    @Override
    public PagingResponse<RoomJoinRequestResponse> getMyJoinRequests(PagingRequest request) {
        String currentUserId = AuthUtils.getCurrentUserId();

        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Page<RoomJoinRequest> requestPage = roomJoinRequestRepository.findByUserId(currentUserId, pageable);

        return PagingResponse.<RoomJoinRequestResponse>builder()
                .currentPage(request.getPage())
                .pageSize(requestPage.getSize())
                .totalPages(requestPage.getTotalPages())
                .totalElement(requestPage.getTotalElements())
                .data(requestPage.getContent().stream()
                        .map(roomJoinRequestMapper::toRoomJoinRequestResponse)
                        .toList())
                .build();
    }
}

