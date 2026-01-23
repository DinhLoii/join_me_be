package com.join.me.modules.user.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.user.constant.permission.PermissionConstants;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

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
            name = PermissionConstants.COL_CODE,
            nullable = false,
            unique = true,
            columnDefinition = PermissionConstants.CODE_DEFINITION
    )
    String code;

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

    /**
     * Permission ↔ Role (inverse side)
     */
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    Set<Role> roles = new HashSet<>();
}
