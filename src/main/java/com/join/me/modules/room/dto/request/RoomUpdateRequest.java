package com.join.me.modules.room.dto.request;

import com.join.me.modules.room.constant.RoomConstants;
import com.join.me.modules.room.constant.RoomErrorCodeConstants;
import com.join.me.modules.room.enums.JoinMode;
import com.join.me.modules.room.enums.RoomStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomUpdateRequest {

    @Size(min = RoomConstants.TITLE_MIN,
            max = RoomConstants.TITLE_MAX,
            message = RoomErrorCodeConstants.ROOM_TITLE_SIZE_INVALID)
    String title;

    @Size(max = RoomConstants.DESCRIPTION_MAX,
            message = RoomErrorCodeConstants.ROOM_DESCRIPTION_SIZE_INVALID)
    String description;

    @Min(value = RoomConstants.MAX_SLOT_MIN,
            message = RoomErrorCodeConstants.ROOM_MAX_SLOT_INVALID)
    Integer maxSlot;

    JoinMode joinMode;

    RoomStatus status;

    @Size(max = RoomConstants.REQUIREMENTS_MAX_SIZE,
            message = RoomErrorCodeConstants.ROOM_REQUIREMENTS_SIZE_INVALID)
    List<String> requirements;

    @Size(max = RoomConstants.COVER_IMG_MAX_LENGTH,
            message = RoomErrorCodeConstants.ROOM_COVER_IMG_SIZE_INVALID)
    String coverImg;

    @Size(max = RoomConstants.MEETING_POINT_MAX_LENGTH,
            message = RoomErrorCodeConstants.ROOM_MEETING_POINT_SIZE_INVALID)
    String meetingPoint;

    List<String> categoryIds;

    @Size(max = RoomConstants.TAGS_MAX_SIZE,
            message = RoomErrorCodeConstants.ROOM_TAGS_SIZE_INVALID)
    Set<String> tags;
}

