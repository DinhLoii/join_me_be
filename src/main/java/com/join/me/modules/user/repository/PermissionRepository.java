package com.join.me.modules.user.repository;

import com.join.me.modules.user.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    boolean existsByPermissionName(String permissionName);
    Optional<Permission> findByPermissionName(String permissionName);
}
