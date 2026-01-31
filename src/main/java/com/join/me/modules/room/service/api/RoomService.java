package com.join.me.modules.room.service.api;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.room.dto.request.RoomCreationRequest;
import com.join.me.modules.room.dto.request.RoomUpdateRequest;
import com.join.me.modules.room.dto.response.RoomResponse;

public interface RoomService {

    RoomResponse createRoom(RoomCreationRequest request);

    PagingResponse<RoomResponse> getAllOpenRooms(PagingRequest request);

    RoomResponse getRoomById(String roomId);

    PagingResponse<RoomResponse> getMyRooms(PagingRequest request);

    RoomResponse updateMyRoom(String roomId, RoomUpdateRequest request);

    RoomResponse closeMyRoom(String roomId);

    void deleteMyRoom(String roomId);

    void checkAndUpdateExpiredRooms();
}

