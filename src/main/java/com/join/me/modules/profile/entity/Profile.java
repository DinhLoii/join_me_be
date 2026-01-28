package com.join.me.modules.profile.entity;

import com.join.me.common.constants.BaseFieldConstants;
import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.profile.constant.profile.ProfileConstants;
import com.join.me.modules.profile.constant.profile.ProfileErrorCodeConstants;
import com.join.me.modules.profile.enums.GenderEnums;
import com.join.me.modules.profile.enums.RelationshipStatus;
import com.join.me.modules.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = ProfileConstants.TABLE_PROFILE)
public class Profile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProfileConstants.COL_USER_ID,
            referencedColumnName = BaseFieldConstants.ID)
    @MapsId
    @NotNull(message = ProfileErrorCodeConstants.PROFILE_USER_REQUIRED)
    User user;

    @Column(name = ProfileConstants.COL_FULL_NAME,
            columnDefinition = ProfileConstants.FULL_NAME_DEFINITION)
    String fullName;

    @Column(name = ProfileConstants.COL_AVATAR_URL,
            columnDefinition = ProfileConstants.AVATAR_URL_DEFINITION)
    String avatarUrl;

    @Column(name = ProfileConstants.COL_BIO,
            columnDefinition = ProfileConstants.BIO_DEFINITION)
    String bio;

    @OneToOne(mappedBy = "profile")
    Address address;

    @OneToMany(
            mappedBy = "profile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<PlatformLink> socialUrls;

    @Column(name = ProfileConstants.COL_DOB,
            columnDefinition = ProfileConstants.DOB_DEFINITION)
    LocalDate dob;

    @Enumerated(value = EnumType.STRING)
    @Column(name = ProfileConstants.COL_RELATIONSHIP_STATUS,
            columnDefinition = ProfileConstants.RELATIONSHIP_STATUS_DEFINITION)
    RelationshipStatus relationshipStatus;

    @Column(name = ProfileConstants.COL_BACKGROUND_URL,
            columnDefinition = ProfileConstants.BACKGROUND_URL_DEFINITION)
    String backgroundUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = ProfileConstants.COL_GENDER,
            columnDefinition = ProfileConstants.GENDER_DEFINITION)
    GenderEnums gender;

    @ManyToOne
    @JoinColumn(name = ProfileConstants.COL_UNIVERSITY_ID,
            referencedColumnName = BaseFieldConstants.ID)
    University university;

    @ElementCollection
    @CollectionTable(
            name = "hobbies",
            joinColumns = @JoinColumn(name = ProfileConstants.COL_USER_ID)
    )
    @Column(name = ProfileConstants.COL_HOBBIES)
    List<String> hobbies;
}
