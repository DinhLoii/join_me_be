package com.join.me.modules.room.repository;

import com.join.me.modules.room.entity.RoomMember;
import com.join.me.modules.room.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, String> {
    List<RoomMember> findByRoomId(String roomId);

    List<RoomMember> findByUserId(String userId);

    Optional<RoomMember> findByRoomIdAndUserId(String roomId, String userId);

    List<RoomMember> findByStatus(MemberStatus status);

    long countByRoomIdAndStatus(String roomId, MemberStatus status);

    boolean existsByRoomIdAndUserId(String roomId, String userId);

    List<RoomMember> findByUserIdAndCooldownUntilAfter(String userId, Instant now);
}

