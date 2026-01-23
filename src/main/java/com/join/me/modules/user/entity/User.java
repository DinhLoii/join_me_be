package com.join.me.modules.user.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.user.constant.user.UserConstants;
//import com.join.me.modules.user.enums.UserStatus; // Enums chưa làm
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

    @Enumerated(EnumType.STRING)
    @Column(
            name = UserConstants.COL_STATUS,
            nullable = false,
            columnDefinition = UserConstants.STATUS_DEFINITION
    )

//    UserStatus status; // Enums chưa làm

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
