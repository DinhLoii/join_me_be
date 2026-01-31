package com.join.me.modules.room.service.api;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.room.dto.request.RoomJoinRequestCreationRequest;
import com.join.me.modules.room.dto.response.RoomJoinRequestResponse;

public interface RoomJoinRequestService {

    RoomJoinRequestResponse createJoinRequest(RoomJoinRequestCreationRequest request);

    RoomJoinRequestResponse acceptJoinRequest(String requestId);

    RoomJoinRequestResponse rejectJoinRequest(String requestId);

    PagingResponse<RoomJoinRequestResponse> getRoomJoinRequests(String roomId, PagingRequest request);

    PagingResponse<RoomJoinRequestResponse> getMyJoinRequests(PagingRequest request);
}

