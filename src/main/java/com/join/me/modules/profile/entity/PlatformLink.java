package com.join.me.modules.profile.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.profile.constant.address.PlatformLinkConstants;
import com.join.me.modules.profile.enums.Platform;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = PlatformLinkConstants.PLATFORM_LINK_TABLE)
public class PlatformLink extends BaseEntity {
    Platform platform;
    String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PlatformLinkConstants.COL_PROFILE_ID)
    private Profile profile;
}
