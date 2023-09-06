package com.Database.Project.Repository;

import com.Database.Project.Model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByPermissionName(String permissionName);
}