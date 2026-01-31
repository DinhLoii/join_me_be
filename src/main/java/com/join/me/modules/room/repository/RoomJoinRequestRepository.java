package com.join.me.modules.room.repository;

import com.join.me.modules.room.entity.RoomJoinRequest;
import com.join.me.modules.room.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomJoinRequestRepository extends JpaRepository<RoomJoinRequest, String> {
    List<RoomJoinRequest> findByRoomId(String roomId);

    List<RoomJoinRequest> findByUserId(String userId);

    Optional<RoomJoinRequest> findByRoomIdAndUserId(String roomId, String userId);

    List<RoomJoinRequest> findByStatus(RequestStatus status);

    boolean existsByRoomIdAndUserIdAndStatus(String roomId, String userId, RequestStatus status);
}

