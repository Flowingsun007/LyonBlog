package com.flowingsun.behavior.dao;

import com.flowingsun.behavior.entity.CommentLike;
import org.springframework.stereotype.Component;

@Component
public interface CommentLikeMapper {

    int deleteByCommentId(int id);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentLike record);

    int insertSelective(CommentLike record);

    CommentLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentLike record);

    int updateByPrimaryKey(CommentLike record);

    int selectLikeCountByCommentLikeBean(CommentLike bean);
}