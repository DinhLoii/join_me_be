package com.join.me.modules.auth.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.auth.constants.AuthConstants;
import com.join.me.modules.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
    name = AuthConstants.EMAIL_VERIFICATION_TOKENS,
    indexes = {
        @Index(name = "idx_token_jti", columnList = "jti_hash"),
        @Index(name = "idx_token_user", columnList = "user_id")
    }
)
public class EmailVerificationToken extends BaseEntity {

    @Column(name = AuthConstants.JTIHASH, nullable = false, unique = true)
    String jtiHash;

    @ManyToOne(optional = false)
    @JoinColumn(name = AuthConstants.USER_ID, nullable = false)
    User user;

    @Column(name = AuthConstants.EXPIRES_AT, nullable = false)
    LocalDateTime expiresAt;

    @Column(name = AuthConstants.VERIFIED_AT)
    LocalDateTime verifiedAt;
}

