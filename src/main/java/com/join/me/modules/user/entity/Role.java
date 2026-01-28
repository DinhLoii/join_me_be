package com.join.me.modules.user.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.user.constants.role.RoleConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = RoleConstants.TABLE_ROLE)
public class Role extends BaseEntity {

        @Column(name = RoleConstants.COL_ROLE_NAME,
                nullable = false, unique = true,
                columnDefinition = RoleConstants.ROLE_NAME_DEFINITION)
        @Size(min = RoleConstants.MIN_CHARS_ROLE_NAME,
                max = RoleConstants.MAX_CHARS_ROLE_NAME)
        String roleName;

        @ManyToMany(fetch = FetchType.LAZY)
        Set<Permission> permissions;
}