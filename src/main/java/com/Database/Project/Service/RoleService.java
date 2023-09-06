package com.Database.Project.Service;

import com.Database.Project.Model.Permission;
import com.Database.Project.Model.Role;
import com.Database.Project.Model.User;
import com.Database.Project.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public Role getRoleById(Long roleId){
        return roleRepository.getById(roleId);
    }

    public Role assignPermissions(Role role, Set<Permission> permissions) {
        role.getPermissionsList().addAll(permissions);
        return roleRepository.save(role);
    }
}