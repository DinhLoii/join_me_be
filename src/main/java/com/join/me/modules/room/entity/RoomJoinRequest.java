package com.join.me.modules.room.entity;

import com.join.me.common.constants.BaseFieldConstants;
import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.room.constant.RoomConstants;
import com.join.me.modules.room.enums.RequestStatus;
import com.join.me.modules.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = RoomConstants.TABLE_ROOM_JOIN_REQUEST)
public class RoomJoinRequest extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = RoomConstants.COL_STATUS,
            nullable = false,
            columnDefinition = RoomConstants.STATUS_DEFINITION)
    @Builder.Default
    RequestStatus status = RequestStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = RoomConstants.COL_ROOM_ID,
            referencedColumnName = BaseFieldConstants.ID,
            nullable = false,
            columnDefinition = RoomConstants.ROOM_ID_DEFINITION)
    Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = RoomConstants.COL_USER_ID,
            referencedColumnName = BaseFieldConstants.ID,
            nullable = false,
            columnDefinition = RoomConstants.USER_ID_DEFINITION)
    User user;
}

