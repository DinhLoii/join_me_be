package com.join.me.modules.user.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.user.constant.permission.PermissionConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = PermissionConstants.TABLE_PERMISSION)
public class Permission extends BaseEntity {

    @Column(
            name = PermissionConstants.COL_NAME,
            nullable = false,
            columnDefinition = PermissionConstants.NAME_DEFINITION
    )
    String name;

    @Column(
            name = PermissionConstants.COL_DESCRIPTION,
            columnDefinition = PermissionConstants.DESCRIPTION_DEFINITION
    )
    String description;
}
