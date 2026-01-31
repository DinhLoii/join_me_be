package com.join.me.modules.room.service.api;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.room.dto.response.RoomMemberResponse;

public interface RoomMemberService {

    RoomMemberResponse joinRoom(String roomId);

    void leaveRoom(String roomId);

    PagingResponse<RoomMemberResponse> getRoomMembers(String roomId, PagingRequest request);

    void kickMember(String roomId, String memberId);

    void blockMember(String roomId, String memberId);
}

