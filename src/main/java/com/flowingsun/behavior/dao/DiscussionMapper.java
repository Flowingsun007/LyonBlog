package com.flowingsun.behavior.dao;

import com.flowingsun.behavior.entity.Discussion;
import org.springframework.stereotype.Component;

@Component
public interface DiscussionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Discussion record);

    int insertSelective(Discussion record);

    Discussion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Discussion record);

    int updateByPrimaryKey(Discussion record);

    int deleteByCommentId(int commentId);
}