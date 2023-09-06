package com.Database.Project.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissionsList = new HashSet<>();

    public Role() {}

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, Set<Permission> permissions) {
        this.roleName = roleName;
        this.permissionsList = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Permission> getPermissionsList() {
        return this.permissionsList;
    }

    public void setPermissionsList(Set<Permission> permissions) {
        this.permissionsList = permissions;
    }
}