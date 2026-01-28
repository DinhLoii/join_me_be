package com.join.me.modules.user.repository;

import com.join.me.modules.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByRoleName(String roleName);
    Optional<Role> findByRoleName(String roleName);
}
