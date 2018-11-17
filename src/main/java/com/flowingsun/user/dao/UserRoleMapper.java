package com.flowingsun.user.dao;

import com.flowingsun.user.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserRoleMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    Integer insertByUseridRoleid(@Param("userId")Long userId, @Param("roleId")Integer roleId);
}