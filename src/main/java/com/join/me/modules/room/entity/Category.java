package com.join.me.modules.room.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.room.constant.RoomConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = RoomConstants.TABLE_CATEGORY)
public class Category extends BaseEntity {

    @Column(name = RoomConstants.COL_NAME,
            nullable = false,
            unique = true,
            columnDefinition = RoomConstants.NAME_DEFINITION)
    String name;

    @Column(name = "description",
            columnDefinition = "TEXT")
    String description;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    Set<Room> rooms;
}

