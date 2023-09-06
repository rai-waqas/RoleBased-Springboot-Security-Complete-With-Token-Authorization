package com.Database.Project.Controller;

import com.Database.Project.Model.Permission;
import com.Database.Project.Model.Role;
import com.Database.Project.Model.User;
import com.Database.Project.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/roles/")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
    @PostMapping("/roles")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        if (role.getRoleName() == null || role.getRoleName().isEmpty()) {
            return ResponseEntity.badRequest().body("Role name cannot be empty");
        }
        Role createdRole = roleService.createRole(role);
        return ResponseEntity.ok(createdRole);
    }
    @GetMapping("/roles/{roleName}")
    public ResponseEntity<?> getRoleByName(@PathVariable String roleName) {
        if (roleName == null || roleName.isEmpty()) {
            return ResponseEntity.badRequest().body("Role name cannot be empty");
        }
        Role role = roleService.getRoleByName(roleName);
        if (role == null) {
            return ResponseEntity.notFound().build(); // Role not found
        }
        return ResponseEntity.ok(role);
    }
    @PutMapping("/roles/{roleId}/assign")
    public ResponseEntity<?> assignPermissions(@PathVariable Long roleId, @RequestBody Set<Permission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return ResponseEntity.badRequest().body("Permissions cannot be empty");
        }
        try {
            Role role = roleService.getRoleById(roleId);
            if (role == null) {
                return ResponseEntity.notFound().build(); // Role not found
            }
            Role updatedRole = roleService.assignPermissions(role, permissions);
            return ResponseEntity.ok(updatedRole);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}