package com.join.me.modules.room.dto.request;

import com.join.me.modules.room.constant.RoomErrorCodeConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomJoinRequestCreationRequest {
    @NotBlank(message = RoomErrorCodeConstants.REQUEST_ROOM_ID_NOT_BLANK)
    String roomId;
}

