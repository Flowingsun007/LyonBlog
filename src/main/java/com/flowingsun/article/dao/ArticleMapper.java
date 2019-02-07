package com.flowingsun.article.dao;

import com.flowingsun.admin.dto.AdminBlogQuery;
import com.flowingsun.article.dto.ArticleInfo;
import com.flowingsun.article.dto.ArticleTag;
import com.flowingsun.article.dto.RegularRecommend;
import com.flowingsun.article.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


import java.util.List;
@Component
public interface ArticleMapper {
    /**
     * 查询所有文章主分类
     */
    List<Category> selectMainCategory();
    /**
     * 根据文章标签名查标签ID
     */
    int selectTagIdByTagName(String tagName);
    /**
     * 查询文章所有标签名
     */
    List<String> selectTagsNameByPrimarykey(Integer id);
    /**
     * 查询文章信息
     */
    ArticleInfo selectInfoByPrimaryKey(Integer articleId);
    /**
     * 删除文章
     */
    Integer deleteByPrimaryKey(Integer id);
    /**
     * 给文章插入一条标签关系
     */
    int insertTagRelation(ArticleTag articleTag);
    /**
     * 新增一个标签
     */
    int insertNewTag(ArticleTag articleTag);
    /**
     * 新增一篇文章
     */
    int insertNewArticle(Article article);

    int insertSelective(Article record);
    /**
     * 根据ID查询文章
     */
    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);
    /**
     * 编辑(更新)文章
     */
    int updateByPrimaryKey(Article record);
    /**
     * 查询所有标签
     */
    List<ArticleTag> selectAllTag();
    /**
     * 查询文章所有标签
     */
    List<ArticleTag> selectArticleTagsByPrimarykey(Integer id);
    /**
     * 根据主分类id查主分类名称
     */
    String selectMainCategoryNameById(Integer id);
    /**
     * 根据二级分类id查二级分类名称
     */
    String selectSecondCategoryNameById(Integer id);
    /**
     * 根据cId查分类文章
     */
    List<Article> selectCategoryArticles(@Param("cId") Integer cId, @Param("startNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    /**
     * 根据cId,userId查用户分类文章
     */
    List<Article> selectUserCategoryArticles(@Param("cId") Integer cId, @Param("userid") Integer userid, @Param("startNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    /**
     * 根据tagId查分类文章
     */
    List<Article> selectTagArticles(@Param("tagId") Integer tagId, @Param("startNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    /**
     * 根据主分类id查对应二级分类
     */
    List<Category> selectSecondCatogoryByMid(Integer mId);
    /**
     * 根据cId查文章总数
     */
    Integer selectCategoryArticlesCount(Integer cId);
    /**
     * 根据cId查用户创作的文章总数
     */
    Integer selectUserCategoryArticlesCount(@Param("cId") Integer cId, @Param("userId") Integer userId);
    /**
     * 根据mId查文章总数
     */
    Integer selectMainCategoryArticlesCount(Integer mId);
    /**
     * 根据mId,userId查文章总数
     */
    Integer selectUserMainCategoryArticlesCount(@Param("mId") Integer mId, @Param("userId") Integer userId);
    /**
     * 检测articleId下的文章是否存在
     */
    Integer selectArticleCountById(Integer articleId);
    /**
     * 查询所有文章数量
     */
    Integer selectAllArticleCount();
    /**
     * 查询所有用户文章数量
     */
    Integer selectUserAllArticleCount(Integer userId);
    /**
     * 根据查询条件查询相应文章
     */
    List<Article> selectAllArticleByQueryCondition(AdminBlogQuery adminBlogQuery);
    /**
     * 根据查询条件查询相应用户文章
     */
    List<Article> selectUserAllArticleByQueryCondition(AdminBlogQuery adminBlogQuery);
    /**
     * 根据tagId查询拥有此标签的文章数
     */
    Integer selectTagCountByTagId(Integer tagId);
    /**
     * 删除一条文章标签关系
     */
    Integer deleteTagRelation(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);
    /**
     * 删除一个tag标签
     */
    Integer deleteTagByTagId(Integer tagId);
    /**
     * 批量修改文章分类
     */
    int changeArticleCategoryById(Category articles);
    /**
     * 查询一条文章标签关系
     */
    Integer selectTagRelationByTagIdArticleId(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);
    /**
     * 文章推荐：【上一页】
     */
    RegularRecommend selectPreviousArticle(RegularRecommend recommendBean);
    /**
     * 文章推荐：【下一页】
     */
    RegularRecommend selectNextArticle(RegularRecommend recommendBean);

}