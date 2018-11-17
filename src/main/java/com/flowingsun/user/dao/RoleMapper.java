package com.flowingsun.user.dao;

import com.flowingsun.user.entity.Role;
import org.springframework.stereotype.Component;

@Component
public interface RoleMapper {
    int deleteByPrimaryKey(Byte roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Byte roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}