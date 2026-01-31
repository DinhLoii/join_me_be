package com.join.me.modules.room.mapper;

import com.join.me.modules.room.dto.request.RoomCreationRequest;
import com.join.me.modules.room.dto.request.RoomUpdateRequest;
import com.join.me.modules.room.dto.response.CategoryResponse;
import com.join.me.modules.room.dto.response.RoomResponse;
import com.join.me.modules.room.entity.Category;
import com.join.me.modules.room.entity.Room;
import com.join.me.modules.room.enums.MemberStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface RoomMapper {

    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "joinRequests", ignore = true)
    Room toRoom(RoomCreationRequest request);

    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "joinRequests", ignore = true)
    void updateRoom(@MappingTarget Room room, RoomUpdateRequest request);

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "ownerUsername", source = "owner.username")
    @Mapping(target = "categories", expression = "java(mapCategories(room.getCategories()))")
    @Mapping(target = "currentMemberCount", expression = "java(calculateMemberCount(room))")
    @Mapping(target = "isOwner", ignore = true)
    RoomResponse toRoomResponse(Room room);

    default List<CategoryResponse> mapCategories(Set<Category> categories) {
        if (categories == null) {
            return null;
        }
        CategoryMapper categoryMapper = org.mapstruct.factory.Mappers.getMapper(CategoryMapper.class);
        return categories.stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    default Integer calculateMemberCount(Room room) {
        if (room == null || room.getMembers() == null) {
            return 0;
        }
        return (int) room.getMembers().stream()
                .filter(member -> member.getStatus() == MemberStatus.JOINED)
                .count();
    }
}

