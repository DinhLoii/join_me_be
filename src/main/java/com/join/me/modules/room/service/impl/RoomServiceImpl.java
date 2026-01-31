package com.join.me.modules.room.service.impl;

import com.join.me.common.constants.GlobalVariableConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.common.utils.PagingUtil;
import com.join.me.modules.auth.utils.AuthUtils;
import com.join.me.modules.room.dto.request.RoomCreationRequest;
import com.join.me.modules.room.dto.request.RoomUpdateRequest;
import com.join.me.modules.room.dto.response.RoomResponse;
import com.join.me.modules.room.entity.Category;
import com.join.me.modules.room.entity.Room;
import com.join.me.modules.room.entity.RoomMember;
import com.join.me.modules.room.enums.MemberRole;
import com.join.me.modules.room.enums.MemberStatus;
import com.join.me.modules.room.enums.RoomStatus;
import com.join.me.modules.room.mapper.RoomMapper;
import com.join.me.modules.room.repository.CategoryRepository;
import com.join.me.modules.room.repository.RoomMemberRepository;
import com.join.me.modules.room.repository.RoomRepository;
import com.join.me.modules.room.service.api.RoomService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoomServiceImpl implements RoomService {

    RoomRepository roomRepository;
    RoomMapper roomMapper;
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    RoomMemberRepository roomMemberRepository;

    @Override
    @Transactional
    public RoomResponse createRoom(RoomCreationRequest request) {
        String currentUserId = AuthUtils.getCurrentUserId();
        User owner = userRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_USERNAME_NOT_FOUND));

        Room room = roomMapper.toRoom(request);
        room.setOwner(owner);

        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            Set<Category> categories = request.getCategoryIds().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)))
                    .collect(Collectors.toSet());
            room.setCategories(categories);
        }

        Room savedRoom = roomRepository.save(room);

        RoomMember ownerMember = RoomMember.builder()
                .room(savedRoom)
                .user(owner)
                .role(MemberRole.OWNER)
                .status(MemberStatus.JOINED)
                .joinedAt(Instant.now())
                .build();
        roomMemberRepository.save(ownerMember);

        RoomResponse response = roomMapper.toRoomResponse(savedRoom);
        setRoomResponseOwnerFlag(response, currentUserId);
        return response;
    }

    @Override
    @Transactional
    public PagingResponse<RoomResponse> getAllOpenRooms(PagingRequest request) {
        checkAndUpdateExpiredRooms();

        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Instant now = Instant.now();
        Page<Room> roomPage = roomRepository.findOpenRoomsNotExpired(RoomStatus.OPEN, now, pageable);

        String currentUserId = AuthUtils.getCurrentUserId();
        return getRoomResponsePagingResponse(request, currentUserId, roomPage);
    }

    @Override
    public RoomResponse getRoomById(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        if (isRoomExpired(room) && room.getStatus() == RoomStatus.OPEN) {
            room.setStatus(RoomStatus.CLOSED);
            roomRepository.save(room);
        }

        RoomResponse response = roomMapper.toRoomResponse(room);
        String currentUserId = AuthUtils.getCurrentUserId();
        setRoomResponseOwnerFlag(response, currentUserId);
        return response;
    }

    @Override
    public PagingResponse<RoomResponse> getMyRooms(PagingRequest request) {
        String currentUserId = AuthUtils.getCurrentUserId();

        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Page<Room> roomPage = roomRepository.findByOwnerId(currentUserId, pageable);

        return getRoomResponsePagingResponse(request, currentUserId, roomPage);
    }

    private PagingResponse<RoomResponse> getRoomResponsePagingResponse(PagingRequest request, String currentUserId, Page<Room> roomPage) {
        return PagingResponse.<RoomResponse>builder()
                .currentPage(request.getPage())
                .pageSize(roomPage.getSize())
                .totalPages(roomPage.getTotalPages())
                .totalElement(roomPage.getTotalElements())
                .data(roomPage.getContent().stream()
                        .map(room -> {
                            RoomResponse response = roomMapper.toRoomResponse(room);
                            setRoomResponseOwnerFlag(response, currentUserId);
                            return response;
                        })
                        .toList())
                .build();
    }

    @Override
    @Transactional
    public RoomResponse updateMyRoom(String roomId, RoomUpdateRequest request) {
        String currentUserId = AuthUtils.getCurrentUserId();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        validateRoomOwnership(room, currentUserId);
        validateRoomCanBeUpdated(room);

        roomMapper.updateRoom(room, request);

        if (request.getCategoryIds() != null) {
            if (request.getCategoryIds().isEmpty()) {
                room.setCategories(new HashSet<>());
            } else {
                Set<Category> categories = request.getCategoryIds().stream()
                        .map(categoryId -> categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)))
                        .collect(Collectors.toSet());
                room.setCategories(categories);
            }
        }

        Room saved = roomRepository.save(room);
        RoomResponse response = roomMapper.toRoomResponse(saved);
        setRoomResponseOwnerFlag(response, currentUserId);
        return response;
    }

    @Override
    @Transactional
    public RoomResponse closeMyRoom(String roomId) {
        String currentUserId = AuthUtils.getCurrentUserId();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        validateRoomOwnership(room, currentUserId);

        room.setStatus(RoomStatus.CLOSED);
        Room saved = roomRepository.save(room);

        RoomResponse response = roomMapper.toRoomResponse(saved);
        setRoomResponseOwnerFlag(response, currentUserId);
        return response;
    }

    @Override
    @Transactional
    public void deleteMyRoom(String roomId) {
        String currentUserId = AuthUtils.getCurrentUserId();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        validateRoomOwnership(room, currentUserId);
        validateRoomCanBeDeleted(room);

        roomRepository.delete(room);
    }

    @Override
    @Transactional
    public void checkAndUpdateExpiredRooms() {
        Instant now = Instant.now();
        List<Room> expiredRooms = roomRepository.findExpiredRoomsByStatus(now, RoomStatus.OPEN);

        if (!expiredRooms.isEmpty()) {
            expiredRooms.forEach(room -> room.setStatus(RoomStatus.CLOSED));
            roomRepository.saveAll(expiredRooms);
            log.info("Updated {} expired rooms to CLOSED status", expiredRooms.size());
        }
    }

    private void validateRoomOwnership(Room room, String userId) {
        if (!room.getOwner().getId().equals(userId)) {
            throw new AppException(ErrorCode.ROOM_ACCESS_DENIED);
        }
    }

    private void validateRoomCanBeUpdated(Room room) {
        long activeMemberCount = countActiveMembers(room.getId());
        if (activeMemberCount > 1) {
            throw new AppException(ErrorCode.ROOM_UPDATE_NOT_ALLOWED);
        }
    }

    private void validateRoomCanBeDeleted(Room room) {
        long activeMemberCount = countActiveMembers(room.getId());
        if (activeMemberCount > 1) {
            throw new AppException(ErrorCode.ROOM_DELETE_NOT_ALLOWED);
        }
    }

    private long countActiveMembers(String roomId) {
        return roomMemberRepository.countByRoomIdAndStatus(roomId, MemberStatus.JOINED);
    }

    private boolean isRoomExpired(Room room) {
        return room.getExpiresAt() != null && room.getExpiresAt().isBefore(Instant.now());
    }

    private void setRoomResponseOwnerFlag(RoomResponse response, String currentUserId) {
        if (response != null && response.getOwnerId() != null) {
            response.setIsOwner(response.getOwnerId().equals(currentUserId));
        }
    }
}

