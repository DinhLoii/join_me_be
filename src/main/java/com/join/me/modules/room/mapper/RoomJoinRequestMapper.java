package com.join.me.modules.room.mapper;

import com.join.me.modules.room.dto.request.RoomJoinRequestCreationRequest;
import com.join.me.modules.room.dto.request.RoomJoinRequestUpdateRequest;
import com.join.me.modules.room.dto.response.RoomJoinRequestResponse;
import com.join.me.modules.room.entity.RoomJoinRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomJoinRequestMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    RoomJoinRequest toRoomJoinRequest(RoomJoinRequestCreationRequest request);

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateRoomJoinRequest(@MappingTarget RoomJoinRequest roomJoinRequest, RoomJoinRequestUpdateRequest request);

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "roomTitle", source = "room.title")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    RoomJoinRequestResponse toRoomJoinRequestResponse(RoomJoinRequest roomJoinRequest);
}

