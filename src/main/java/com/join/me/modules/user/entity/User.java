package com.join.me.modules.user.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.user.constant.user.UserConstants;
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
@Table(name = UserConstants.TABLE_USER)
public class User extends BaseEntity {

    // =========================
    // AUTH FIELDS
    // =========================
    @Column(
            name = UserConstants.COL_USERNAME,
            nullable = false,
            unique = true,
            columnDefinition = UserConstants.USERNAME_DEFINITION
    )
    String username;

    @Column(
            name = UserConstants.COL_PASSWORD,
            nullable = false,
            columnDefinition = UserConstants.PASSWORD_DEFINITION
    )
    String password;

    // =========================
    // STATUS (ENABLE WHEN ENUM READY)
    // =========================
    /*
    @Enumerated(EnumType.STRING)
    @Column(
            name = UserConstants.COL_STATUS,
            nullable = false,
            columnDefinition = UserConstants.STATUS_DEFINITION
    )
    UserStatus status;
    */

    // =========================
    // RELATIONSHIPS
    // =========================
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = UserConstants.TABLE_USER_ROLE,
            joinColumns = @JoinColumn(
                    name = UserConstants.COL_USER_ID,
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = UserConstants.COL_ROLE_ID,
                    referencedColumnName = "id"
            )
    )
    Set<Role> roles = new HashSet<>();
}
