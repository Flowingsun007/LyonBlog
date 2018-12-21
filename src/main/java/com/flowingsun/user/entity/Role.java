package com.flowingsun.user.entity;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
    private Byte roleid;

    private String role;

    private String description;

    private List<Permission> permissionList;

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public Byte getRoleid() {
        return roleid;
    }

    public void setRoleid(Byte roleid) {
        this.roleid = roleid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleid=" + roleid +
                ", role='" + role + '\'' +
                ", description='" + description + '\'' +
                ", permissionList=" + permissionList +
                '}';
    }
}