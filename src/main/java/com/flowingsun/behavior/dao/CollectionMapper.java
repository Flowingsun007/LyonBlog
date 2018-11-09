package com.flowingsun.behavior.dao;

import com.flowingsun.behavior.entity.Collection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionMapper {

    int deleteByPrimaryKey(Integer id);

    int insertCollection(Collection record);

    int insertSelective(Collection record);

    int selectCollectionCount();

    Collection selectByPrimaryKey(Integer id);

    Integer selectCollectionCountByCollectionbean(Collection thankBean);

    Integer selectCollectionStatusByAidUid(@Param("userId") Long userId, @Param("articleId") Integer articleId);

    int updateByPrimaryKeySelective(Collection record);

    int updateByPrimaryKey(Collection record);

    int selectCollectionCountByUserid(Long userid);

    List<Collection> selectCollectionsByUserid(Long userid);

    int deleteByArticleId(int articleId);
}