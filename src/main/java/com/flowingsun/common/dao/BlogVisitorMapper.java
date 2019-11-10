package com.flowingsun.common.dao;

import com.flowingsun.common.entity.BlogVisitor;
import org.springframework.stereotype.Component;

@Component
public interface BlogVisitorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogVisitor record);

    int insertSelective(BlogVisitor record);

    BlogVisitor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogVisitor record);

    int updateByPrimaryKey(BlogVisitor record);

    int selectVisitorCount();

    int selectViewCount();
}