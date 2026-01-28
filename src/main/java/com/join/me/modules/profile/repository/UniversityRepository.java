package com.join.me.modules.profile.repository;

import com.join.me.modules.profile.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, String> {
}


