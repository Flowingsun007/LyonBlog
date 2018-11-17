package com.flowingsun.behavior.dao;

import com.flowingsun.behavior.entity.Thank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ThankMapper {
    int deleteByPrimaryKey(Integer id);

    int insertThank(Thank record);

    int insertSelective(Thank record);

    int selectThankCount();

    Thank selectByPrimaryKey(Integer id);

    Integer selectThankNumByThankbean(Thank thankBean);

    Integer selectThankStatusByAidUid(@Param("userId") Long userId, @Param("articleId") Integer articleId);

    int updateByPrimaryKeySelective(Thank record);

    int updateByPrimaryKey(Thank record);

    int selectThankCountByUserid(Long userid);

    int deleteByArticleId(int articleId);
}