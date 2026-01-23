package com.join.me.modules.user.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.user.constant.role.RoleConstants;
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
@Table(name = RoleConstants.TABLE_ROLE)
public class Role extends BaseEntity {

    @Column(
            name = RoleConstants.COL_CODE,
            nullable = false,
            unique = true,
            columnDefinition = RoleConstants.CODE_DEFINITION
    )
    String code;

    @Column(
            name = RoleConstants.COL_NAME,
            nullable = false,
            columnDefinition = RoleConstants.NAME_DEFINITION
    )
    String name;

    @Column(
            name = RoleConstants.COL_DESCRIPTION,
            columnDefinition = RoleConstants.DESCRIPTION_DEFINITION
    )
    String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = RoleConstants.TABLE_ROLE_PERMISSION,
            joinColumns = @JoinColumn(
                    name = RoleConstants.COL_ROLE_ID,
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = RoleConstants.COL_PERMISSION_ID,
                    referencedColumnName = "id"
            )
    )
    Set<Permission> permissions = new HashSet<>();
}

