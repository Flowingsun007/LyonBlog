package com.flowingsun.article.dao;

import com.flowingsun.admin.entity.AdminBlogQuery;
import com.flowingsun.article.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;
@Component
public interface ArticleMapper {

    List<Category> selectMainCategory();

    int selectTagIdByTagName(String tagName);

    List<String> selectTagsNameByPrimarykey(Integer id);

    ArticleInfo selectInfoByPrimaryKey(Integer articleId);

    Integer deleteByPrimaryKey(Integer id);

    int insertTagRelation(ArticleTag articleTag);

    int insertNewTag(ArticleTag articleTag);

    int insertNewArticle(Article article);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<ArticleTag> selectAllTag();

    List<ArticleTag> selectArticleTagsByPrimarykey(Integer id);

    String selectMainCategoryNameById(Integer id);

    String selectSecondCategoryNameById(Integer id);

    List<Article> selectCategoryArticles(@Param("cId") Integer cId,@Param("startNum")Integer pageNum,@Param("pageSize")Integer pageSize);

    List<Article> selectTagArticles(@Param("tagId") Integer tagId,@Param("startNum")Integer pageNum,@Param("pageSize")Integer pageSize);

    List<Category> selectSecondCatogoryByMid(Integer mId);

    Integer selectCategoryArticlesCount(Integer cId);

    Integer selectMainCategoryArticlesCount(Integer cId);

    Integer selectArticleCountById(Integer articleId);

    Integer selectAllArticleCount();

    List<Article> selectAllArticleByQueryCondition(AdminBlogQuery adminBlogQuery);

    Integer selectTagCountByTagId(Integer tagId);

    Integer deleteTagRelation(@Param("articleId")Integer articleId, @Param("tagId")Integer tagId);

    Integer deleteTagByTagId(Integer tagId);

    int changeArticleCategoryById(Category articles);

    Integer selectTagRelationByTagIdArticleId(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);

    RegularRecommend selectPreviousArticle(RegularRecommend recommendBean);

    RegularRecommend selectNextArticle(RegularRecommend recommendBean);

}