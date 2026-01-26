package com.join.me.modules.profile.entity;

import com.join.me.modules.profile.constant.profile.ProfileConstants;
import com.join.me.modules.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = ProfileConstants.TABLE_PROFILE)
@jakarta.persistence.EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
public class Profile {

        /**
         * Shared Primary Key:
         * profiles.user_id = users.id
         */
        @Id
        @Column(name = ProfileConstants.COL_USER_ID, nullable = false, columnDefinition = ProfileConstants.USER_ID_DEFINITION)
        String userId;

        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @MapsId
        @JoinColumn(name = ProfileConstants.COL_USER_ID, nullable = false)
        User user;

        @org.springframework.data.annotation.CreatedDate
        @Column(updatable = false)
        java.time.Instant createdAt;

        @org.springframework.data.annotation.LastModifiedDate
        java.time.Instant updatedAt;

        // =========================
        // PROFILE FIELDS
        // =========================
        @Column(name = ProfileConstants.COL_FULL_NAME, columnDefinition = ProfileConstants.FULL_NAME_DEFINITION)
        String fullName;

        @Column(name = ProfileConstants.COL_AVATAR_URL, columnDefinition = ProfileConstants.AVATAR_URL_DEFINITION)
        String avatarUrl;

        @Column(name = ProfileConstants.COL_BIO, columnDefinition = ProfileConstants.BIO_DEFINITION)
        String bio;

        @Column(name = ProfileConstants.COL_ADDRESS, columnDefinition = ProfileConstants.ADDRESS_DEFINITION)
        String address;

        /**
         * social_url[] trong ERD
         * Map dạng TEXT (JSON/CSV) để giữ đúng 1 cột như ERD.
         */
        @Column(name = ProfileConstants.COL_SOCIAL_URLS, columnDefinition = ProfileConstants.SOCIAL_URLS_DEFINITION)
        String socialUrls;

        @Column(name = ProfileConstants.COL_DOB, columnDefinition = ProfileConstants.DOB_DEFINITION)
        LocalDate dob;

        @Column(name = ProfileConstants.COL_RELATIONSHIP_STATUS, columnDefinition = ProfileConstants.RELATIONSHIP_STATUS_DEFINITION)
        String relationshipStatus;

        @Column(name = ProfileConstants.COL_BACKGROUND_URL, columnDefinition = ProfileConstants.BACKGROUND_URL_DEFINITION)
        String backgroundUrl;

        @Column(name = ProfileConstants.COL_GENDER, columnDefinition = ProfileConstants.GENDER_DEFINITION)
        String gender;
}
