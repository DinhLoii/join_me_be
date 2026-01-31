package com.join.me.modules.room.entity;

import com.join.me.common.constants.BaseFieldConstants;
import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.room.constant.RoomConstants;
import com.join.me.modules.room.enums.MemberRole;
import com.join.me.modules.room.enums.MemberStatus;
import com.join.me.modules.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = RoomConstants.TABLE_ROOM_MEMBER)
public class RoomMember extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = RoomConstants.COL_ROLE,
            nullable = false,
            columnDefinition = RoomConstants.ROLE_DEFINITION)
    MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = RoomConstants.COL_STATUS,
            nullable = false,
            columnDefinition = RoomConstants.STATUS_DEFINITION)
    @Builder.Default
    MemberStatus status = MemberStatus.JOINED;

    @Column(name = RoomConstants.COL_JOINED_AT,
            nullable = false,
            columnDefinition = RoomConstants.JOINED_AT_DEFINITION)
    Instant joinedAt;

    @Column(name = RoomConstants.COL_LEFT_AT,
            columnDefinition = RoomConstants.LEFT_AT_DEFINITION)
    Instant leftAt;

    @Column(name = RoomConstants.COL_COOLDOWN_UNTIL,
            columnDefinition = RoomConstants.COOLDOWN_UNTIL_DEFINITION)
    Instant cooldownUntil;

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

    @PreUpdate
    protected void onPreUpdate() {
        if (this.leftAt != null && this.cooldownUntil == null) {
            this.cooldownUntil = this.leftAt.plusSeconds(RoomConstants.COOLDOWN_MINUTES * 60L);
        }
    }
}

