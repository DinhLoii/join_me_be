package com.join.me.modules.user.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.user.constant.role.RoleConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
