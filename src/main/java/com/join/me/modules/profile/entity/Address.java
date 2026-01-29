package com.join.me.modules.profile.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.profile.constant.address.AddressConstants;
import com.join.me.modules.profile.constant.profile.ProfileConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = AddressConstants.ADDRESS_TABLE)
public class Address extends BaseEntity {
    String province;
    String commune;
    @OneToOne()
    @JoinColumn(name = AddressConstants.COL_PROFILE_ID,
            referencedColumnName = ProfileConstants.COL_USER_ID)
    Profile profile;
}
