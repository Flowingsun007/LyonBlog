package com.flowingsun.behavior.dao;

import com.flowingsun.behavior.entity.Collection;
import com.flowingsun.behavior.entity.UrlCollection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UrlCollectionMapper {

    int insertSelective(UrlCollection record);

    int selectUrlCollectionCount();

    UrlCollection selectByPrimaryKey(Integer id);

    List<UrlCollection> selectUrlCollectionsByUserId(Long userId);

    int selectUrlCollectionsByIdAndUserId(@Param("userId") Integer urlCollectionId, @Param("userId") Long userId);

    int updateByPrimaryKeySelective(UrlCollection record);

    int updateByPrimaryKey(UrlCollection record);

    int deleteByPrimaryKey(Integer id);

    void deleteByurlCollectionIdAndUserId(@Param("urlCollectionId") Integer urlCollectionId, @Param("userId") Integer userId);

}