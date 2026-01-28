package com.join.me.modules.profile.entity;

import com.join.me.common.entity.BaseEntity;
import com.join.me.modules.profile.constant.university.UniversityConstants;
import jakarta.persistence.Entity;
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
@Table(name = UniversityConstants.UNIVERSITY)
public class University extends BaseEntity {
    String vietnameseName;
    String englishName;
    String abbreviatedName;
}
