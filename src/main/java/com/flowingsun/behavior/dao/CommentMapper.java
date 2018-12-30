package com.flowingsun.behavior.dao;

import com.flowingsun.behavior.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int selectCommentCount();

    Integer insertSelective(Comment commentBean);

    Comment selectByPrimaryKey(Integer id);

    Integer selectCommentStatusByAidUid(@Param("userId") Long userId, @Param("articleId") Integer articleId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectAllCommentByArticleid(Integer articleId);

    List<Comment> selectCommentByCommentLikeCountDesc(Integer id);

    int selectCommentCountByUserid(Long userid);

    List<Integer> selectCommentsIdByArticleId(int articleId);

    int deleteByArticleId(int articleId);
}