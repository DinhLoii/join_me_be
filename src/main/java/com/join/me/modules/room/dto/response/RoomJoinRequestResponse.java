package com.join.me.modules.room.dto.response;

import com.join.me.modules.room.enums.RequestStatus;
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
public class RoomJoinRequestResponse {
    String id;
    Instant createdAt;
    Instant updatedAt;
    RequestStatus status;
    String roomId;
    String roomTitle;
    String userId;
    String username;
}

