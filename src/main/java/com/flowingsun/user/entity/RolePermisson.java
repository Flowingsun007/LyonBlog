package com.flowingsun.user.entity;

import java.io.Serializable;

public class RolePermisson implements Serializable {
    private Byte roleId;

    private Byte permissionId;

    public Byte getRoleId() {
        return roleId;
    }

    public void setRoleId(Byte roleId) {
        this.roleId = roleId;
    }

    public Byte getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Byte permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermisson{" +
                "roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}