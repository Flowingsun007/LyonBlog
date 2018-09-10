package com.flowingsun.user.entity;

import java.io.Serializable;

public class Permission implements Serializable{
    private Byte permissionid;

    private String permission;

    private String description;

    public Byte getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(Byte permissionid) {
        this.permissionid = permissionid;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}