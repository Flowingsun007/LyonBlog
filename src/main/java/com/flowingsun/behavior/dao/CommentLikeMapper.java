package com.flowingsun.behavior.dao;

import com.flowingsun.behavior.entity.CommentLike;

public interface CommentLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentLike record);

    int insertSelective(CommentLike record);

    CommentLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentLike record);

    int updateByPrimaryKey(CommentLike record);
}