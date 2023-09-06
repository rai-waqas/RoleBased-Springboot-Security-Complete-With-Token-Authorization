package com.Database.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Database.Project.Model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
    Role getById(Long Id);
}