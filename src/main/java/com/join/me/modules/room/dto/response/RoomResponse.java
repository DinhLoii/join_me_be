package com.join.me.modules.room.dto.response;

import com.join.me.modules.room.enums.JoinMode;
import com.join.me.modules.room.enums.RoomStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponse {
    String id;
    Instant createdAt;
    Instant updatedAt;
    String title;
    String description;
    Integer maxSlot;
    JoinMode joinMode;
    RoomStatus status;
    Instant expiresAt;
    List<String> requirements;
    String coverImg;
    String meetingPoint;
    Set<String> tags;
    String ownerId;
    String ownerUsername;
    List<CategoryResponse> categories;
    Integer currentMemberCount;
    Boolean isOwner;
}

