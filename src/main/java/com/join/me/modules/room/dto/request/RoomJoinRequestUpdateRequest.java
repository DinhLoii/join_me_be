package com.join.me.modules.room.dto.request;

import com.join.me.modules.room.constant.RoomErrorCodeConstants;
import com.join.me.modules.room.enums.RequestStatus;
import jakarta.validation.constraints.NotNull;
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
public class RoomJoinRequestUpdateRequest {

    @NotNull(message = RoomErrorCodeConstants.REQUEST_STATUS_NOT_NULL)
    RequestStatus status;
}

