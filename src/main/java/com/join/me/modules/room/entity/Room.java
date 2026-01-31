package com.join.me.modules.room.entity;

import com.join.me.common.constants.BaseFieldConstants;
import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.room.constant.RoomConstants;
import com.join.me.modules.room.enums.JoinMode;
import com.join.me.modules.room.enums.RoomStatus;
import com.join.me.modules.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = RoomConstants.TABLE_ROOM)
public class Room extends BaseEntity {

    @Column(name = RoomConstants.COL_TITLE,
            nullable = false,
            columnDefinition = RoomConstants.TITLE_DEFINITION)
    String title;

    @Column(name = RoomConstants.COL_DESCRIPTION,
            columnDefinition = RoomConstants.DESCRIPTION_DEFINITION)
    String description;

    @Column(name = RoomConstants.COL_MAX_SLOT,
            nullable = false,
            columnDefinition = RoomConstants.MAX_SLOT_DEFINITION)
    Integer maxSlot;

    @Enumerated(EnumType.STRING)
    @Column(name = RoomConstants.COL_JOIN_MODE,
            nullable = false,
            columnDefinition = RoomConstants.JOIN_MODE_DEFINITION)
    JoinMode joinMode;

    @Enumerated(EnumType.STRING)
    @Column(name = RoomConstants.COL_STATUS,
            nullable = false,
            columnDefinition = RoomConstants.STATUS_DEFINITION)
    @Builder.Default
    RoomStatus status = RoomStatus.OPEN;

    @Column(name = RoomConstants.COL_EXPIRES_AT,
            columnDefinition = RoomConstants.EXPIRES_AT_DEFINITION)
    Instant expiresAt;

    @Column(name = RoomConstants.COL_COVER_IMG,
            columnDefinition = RoomConstants.COVER_IMG_DEFINITION)
    String coverImg;

    @Column(name = RoomConstants.COL_MEETING_POINT,
            columnDefinition = RoomConstants.MEETING_POINT_DEFINITION)
    String meetingPoint;

    @ElementCollection
    @CollectionTable(
            name = "room_requirements",
            joinColumns = @JoinColumn(name = RoomConstants.COL_ROOM_ID)
    )
    @Column(name = "requirement", columnDefinition = RoomConstants.REQUIREMENTS_DEFINITION)
    List<String> requirements;

    @ElementCollection
    @CollectionTable(
            name = "room_tags",
            joinColumns = @JoinColumn(name = RoomConstants.COL_ROOM_ID)
    )
    @Column(name = "tag", columnDefinition = RoomConstants.TAGS_DEFINITION)
    Set<String> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = RoomConstants.COL_OWNER_ID,
            referencedColumnName = BaseFieldConstants.ID,
            nullable = false,
            columnDefinition = RoomConstants.OWNER_ID_DEFINITION)
    User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = RoomConstants.TABLE_ROOM_CATEGORIES,
            joinColumns = @JoinColumn(name = RoomConstants.COL_ROOM_ID),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    Set<Category> categories;

    @OneToMany(
            mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<RoomMember> members;

    @OneToMany(
            mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<RoomJoinRequest> joinRequests;

    @PrePersist
    protected void onPrePersist() {
        if (this.expiresAt == null) {
            Instant now = Instant.now();
            this.expiresAt = now.plusSeconds(RoomConstants.DEFAULT_EXPIRY_DAYS * 24L * 60L * 60L);
        }
    }
}

