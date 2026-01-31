package com.join.me.modules.room.dto.request;

import com.join.me.modules.room.constant.RoomConstants;
import com.join.me.modules.room.constant.RoomErrorCodeConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CategoryCreationRequest {

    @NotBlank(message = RoomErrorCodeConstants.CATEGORY_NAME_NOT_BLANK)
    @Size(min = RoomConstants.NAME_MIN,
            max = RoomConstants.NAME_MAX,
            message = RoomErrorCodeConstants.CATEGORY_NAME_SIZE_INVALID)
    String name;

    @Size(max = RoomConstants.DESCRIPTION_CATEGORY_MAX,
            message = RoomErrorCodeConstants.CATEGORY_DESCRIPTION_SIZE_INVALID)
    String description;
}

