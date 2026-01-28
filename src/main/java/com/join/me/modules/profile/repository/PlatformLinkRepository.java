package com.join.me.modules.profile.repository;

import com.join.me.modules.profile.entity.PlatformLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformLinkRepository extends JpaRepository<PlatformLink, String> {
    List<PlatformLink> findAllByProfile_Id(String profileId);
}


