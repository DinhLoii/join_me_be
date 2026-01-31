package com.join.me.modules.room.repository;

import com.join.me.modules.room.entity.Room;
import com.join.me.modules.room.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findByOwnerId(String ownerId);

    List<Room> findByStatus(RoomStatus status);

    List<Room> findByCategoriesId(String categoryId);

    List<Room> findByExpiresAtBefore(Instant expiresAt);

    Optional<Room> findByTitle(String title);

    boolean existsByTitle(String title);
}

