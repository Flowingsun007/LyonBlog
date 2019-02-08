package com.flowingsun.article.service;

import com.flowingsun.admin.dto.AdminBlogQuery;
import com.flowingsun.article.dto.ArticleTag;
import com.flowingsun.article.dto.BlogInfo;
import com.flowingsun.article.dto.RegularRecommend;
import com.flowingsun.article.entity.*;
import com.flowingsun.article.vo.CategoryArticleQuery;
import com.flowingsun.article.vo.TagArticleQuery;
import com.flowingsun.common.dto.ResponseDto;

import java.util.List;

public interface ArticleService {

    String categorySelectAjax(Integer mId);

    List<Category> getCategory();

    Category getAllCategory();

    int getTagId(String tagName);

    Article getArticle(Integer id);

    String createArticle(Article article);

    ResponseDto createUserArticle(Article article)throws Exception;

    CategoryArticleQuery getCategoryArticles(Integer cId, CategoryArticleQuery categoryArticleQuery);

    CategoryArticleQuery getUserCategoryArticles(Integer cId, CategoryArticleQuery categoryArticleQuery, Long userId);

    TagArticleQuery getTagArticles(TagArticleQuery queryBean);

    AdminBlogQuery getAllArticles(AdminBlogQuery queryBean);

    AdminBlogQuery getUserAllArticles(AdminBlogQuery queryBean, Long userId);

    List<ArticleTag> selectAllTag();

    void deleteArticleRelations(int articleId);

    String editArticle(Article article);

    String changeArticleCategory(Category articles);

    String deleteOneArticle(Integer articleId);

    String deleteBatchArticle(String articleIdStr);

    Integer addArticleTag(Integer articleId, String tagName) throws Exception;

    Integer addArticleTagQuick(Integer articleId, String tagName) throws Exception;

    Integer addArticleTagQuick(Integer articleId, Integer tagId, String tagName) throws Exception;

    String batchAddArticleTag(ArticleTag tagBean);

    Integer deleteArticleOneTag(Integer articleId, String tagName);

    String resetArticleTag(ArticleTag tagBean);

    String batchResetArticleTag(ArticleTag tagBean);

    Integer createOneTag(ArticleTag tagBean);

    Integer createOneTag(String tagName);

    Integer createOneTagRelation(ArticleTag tagBean);

    Integer createOneTagRelation(Integer articleId, Integer tagId, String tagName);

    Integer deleteOneTag(Integer tagId);

    Integer deleteOneTagRelation(Integer tagId, Integer articleId);

    boolean checkTagExist(String tagName);

    boolean checkTagRelationExist(Integer articleId, Integer tagId);

    boolean checkArticleExist(Integer articleId);

    String deleteArticleAllTag(ArticleTag tagBean);

    Integer deleteArticleAllTag(Integer articleId);

    String batchDeleteArticleTag(ArticleTag tagBean);

    String batchDeleteArticleAllTag(ArticleTag tagBean);

    RegularRecommend getRegularRecommendArticle(Integer articleId);

    BlogInfo selectInfomation();
}
