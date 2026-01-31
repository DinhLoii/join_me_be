package com.join.me.modules.room.repository;

import com.join.me.modules.room.entity.Room;
import com.join.me.modules.room.enums.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findByOwnerId(String ownerId);

    Page<Room> findByOwnerId(String ownerId, Pageable pageable);

    List<Room> findByStatus(RoomStatus status);

    @Query("SELECT r FROM Room r WHERE r.status = :status AND r.expiresAt > :now")
    Page<Room> findOpenRoomsNotExpired(@Param("status") RoomStatus status, @Param("now") Instant now, Pageable pageable);

    List<Room> findByCategoriesId(String categoryId);

    @Query("SELECT r FROM Room r WHERE r.expiresAt < :now AND r.status = :status")
    List<Room> findExpiredRoomsByStatus(@Param("now") Instant now, @Param("status") RoomStatus status);

    List<Room> findByExpiresAtBefore(Instant expiresAt);

    Optional<Room> findByTitle(String title);

    boolean existsByTitle(String title);
}

