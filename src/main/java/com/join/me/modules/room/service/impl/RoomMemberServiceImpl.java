package com.join.me.modules.room.service.impl;

import com.join.me.common.constants.GlobalVariableConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.common.utils.PagingUtil;
import com.join.me.modules.auth.utils.AuthUtils;
import com.join.me.modules.room.dto.response.RoomMemberResponse;
import com.join.me.modules.room.entity.Room;
import com.join.me.modules.room.entity.RoomMember;
import com.join.me.modules.room.enums.JoinMode;
import com.join.me.modules.room.enums.MemberRole;
import com.join.me.modules.room.enums.MemberStatus;
import com.join.me.modules.room.enums.RoomStatus;
import com.join.me.modules.room.mapper.RoomMemberMapper;
import com.join.me.modules.room.repository.RoomMemberRepository;
import com.join.me.modules.room.repository.RoomRepository;
import com.join.me.modules.room.service.api.RoomMemberService;
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
public class RoomMemberServiceImpl implements RoomMemberService {

    RoomMemberRepository roomMemberRepository;
    RoomRepository roomRepository;
    RoomMemberMapper roomMemberMapper;
    UserRepository userRepository;

    @Override
    @Transactional
    public RoomMemberResponse joinRoom(String roomId) {
        String currentUserId = AuthUtils.getCurrentUserId();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        if (room.getStatus() != RoomStatus.OPEN) {
            throw new AppException(ErrorCode.ROOM_EXPIRED);
        }

        if (room.getExpiresAt() != null && room.getExpiresAt().isBefore(Instant.now())) {
            throw new AppException(ErrorCode.ROOM_EXPIRED);
        }

        if (room.getJoinMode() != JoinMode.AUTO) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        if (roomMemberRepository.existsByRoomIdAndUserId(roomId, currentUserId)) {
            RoomMember existingMember = roomMemberRepository.findByRoomIdAndUserId(roomId, currentUserId)
                    .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));
            if (existingMember.getStatus() == MemberStatus.JOINED) {
                throw new AppException(ErrorCode.MEMBER_ALREADY_EXISTS);
            }
        }

        if (!roomMemberRepository.findByUserIdAndCooldownUntilAfter(currentUserId, Instant.now()).isEmpty()) {
            throw new AppException(ErrorCode.MEMBER_IN_COOLDOWN);
        }

        long activeMemberCount = roomMemberRepository.countByRoomIdAndStatus(roomId, MemberStatus.JOINED);
        if (activeMemberCount >= room.getMaxSlot()) {
            throw new AppException(ErrorCode.ROOM_FULL);
        }

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_USERNAME_NOT_FOUND));

        RoomMember member = RoomMember.builder()
                .room(room)
                .user(user)
                .role(MemberRole.MEMBER)
                .status(MemberStatus.JOINED)
                .joinedAt(Instant.now())
                .build();

        RoomMember saved = roomMemberRepository.save(member);
        return roomMemberMapper.toRoomMemberResponse(saved);
    }

    @Override
    @Transactional
    public void leaveRoom(String roomId) {
        String currentUserId = AuthUtils.getCurrentUserId();

        roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        RoomMember member = roomMemberRepository.findByRoomIdAndUserId(roomId, currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getRole() == MemberRole.OWNER) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        member.setStatus(MemberStatus.LEFT);
        member.setLeftAt(Instant.now());
        roomMemberRepository.save(member);
    }

    @Override
    public PagingResponse<RoomMemberResponse> getRoomMembers(String roomId, PagingRequest request) {
        roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Page<RoomMember> memberPage = roomMemberRepository.findByRoomId(roomId, pageable);

        return PagingResponse.<RoomMemberResponse>builder()
                .currentPage(request.getPage())
                .pageSize(memberPage.getSize())
                .totalPages(memberPage.getTotalPages())
                .totalElement(memberPage.getTotalElements())
                .data(memberPage.getContent().stream()
                        .map(roomMemberMapper::toRoomMemberResponse)
                        .toList())
                .build();
    }

    @Override
    @Transactional
    public void kickMember(String roomId, String memberId) {
        String currentUserId = AuthUtils.getCurrentUserId();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        if (!room.getOwner().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        RoomMember member = roomMemberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.getRoom().getId().equals(roomId)) {
            throw new AppException(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (member.getRole() == MemberRole.OWNER) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        member.setStatus(MemberStatus.KICKED);
        member.setLeftAt(Instant.now());
        roomMemberRepository.save(member);
    }

    @Override
    @Transactional
    public void blockMember(String roomId, String memberId) {
        String currentUserId = AuthUtils.getCurrentUserId();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        if (!room.getOwner().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        RoomMember member = roomMemberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.getRoom().getId().equals(roomId)) {
            throw new AppException(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (member.getRole() == MemberRole.OWNER) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        member.setStatus(MemberStatus.BLOCKING);
        roomMemberRepository.save(member);
    }
}

