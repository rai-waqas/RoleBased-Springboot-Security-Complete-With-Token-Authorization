package com.Database.Project.Controller;

import com.Database.Project.Model.Permission;
import com.Database.Project.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @PostMapping("/permissions")
    public ResponseEntity<?> createPermission(@RequestBody Permission permission) {
        if (permission.getPermissionName() == null || permission.getPermissionName().isEmpty()) {
            return ResponseEntity.badRequest().body("Permission name cannot be empty");
        }
        Permission createdPermission = permissionService.createPermission(permission);
        return ResponseEntity.ok(createdPermission);
    }
    @GetMapping("/permissions")
    public ResponseEntity<?> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        if (permissions.isEmpty()) {
            return ResponseEntity.badRequest().body("No permissions found for the user");
        }
        return ResponseEntity.ok(permissions);
    }


}