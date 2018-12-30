package com.flowingsun.user.dao;

import com.flowingsun.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    Integer insertByUserRegister(User user);

    int insertSelective(User record);

    Long selectUseridByUserphone(String userphone);

    User selectByPrimaryKey(Long id);

    User selectByUserToken(User user);

    int selectUserCount();

    int updateByPrimaryKey(User record);

    int updateByPrimaryKeySelective(User record);

    Integer updateUserStatusByUserphone(@Param("userstatus") int userstatus, @Param("userphone") String userphone);

    Integer deleteByUserphone(String userphone);

    int deleteByPrimaryKey(Long id);

}