package com.join.me.modules.room.dto.response;

import com.join.me.modules.room.enums.MemberRole;
import com.join.me.modules.room.enums.MemberStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomMemberResponse {
    String id;
    Instant createdAt;
    Instant updatedAt;
    MemberRole role;
    MemberStatus status;
    Instant joinedAt;
    Instant leftAt;
    Instant cooldownUntil;
    String roomId;
    String roomTitle;
    String userId;
    String username;
}

