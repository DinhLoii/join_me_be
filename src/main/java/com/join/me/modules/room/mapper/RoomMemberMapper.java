package com.join.me.modules.room.mapper;

import com.join.me.modules.room.dto.response.RoomMemberResponse;
import com.join.me.modules.room.entity.RoomMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMemberMapper {

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "roomTitle", source = "room.title")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    RoomMemberResponse toRoomMemberResponse(RoomMember roomMember);
}

