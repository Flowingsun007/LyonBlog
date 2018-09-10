package com.flowingsun.user.dao;

import com.flowingsun.user.entity.Permission;

public interface PermissionMapper {
    int deleteByPrimaryKey(Byte permissionid);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Byte permissionid);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}