package com.flowingsun.article.service;

import com.flowingsun.admin.entity.AdminBlogQuery;
import com.flowingsun.article.entity.*;
import com.flowingsun.article.vo.CategoryArticleQuery;
import com.flowingsun.article.vo.TagArticleQuery;

import java.util.Date;
import java.util.List;

public interface ArticleService {

    String categorySelectAjax(Integer mId);

    List<Category> getCategory();

    Category getAllCategory();

    int getTagId(String tagName);

    Article getArticle(Integer id);

    //准备删除的方法
//    ArticleInfo getArticleInfo(Integer id);

    String createArticle(Article article);

    CategoryArticleQuery getCategoryArticles(Integer cId, CategoryArticleQuery categoryArticleQuery);

    TagArticleQuery getTagArticles(TagArticleQuery queryBean);

    AdminBlogQuery getAllArticles(AdminBlogQuery queryBean);

    List<ArticleTag> selectAllTag();

    void updateAllTag();

    void deleteArticleRelations(int articleId);

    String editArticle(Article article);

    String changeArticleCategory(Category articles);

    String deleteOneArticle(Integer articleId);

    String deleteBatchArticle(String articleIdStr);

    Integer addArticleTag(Integer articleId, String tagName) throws Exception;

    Integer addArticleTagQuick(Integer articleId, String tagName) throws Exception;

    Integer addArticleTagQuick(Integer articleId, Integer tagId, String tagName) throws Exception;

    String batchAddArticleTag(ArticleTag tagBean);

    Integer deleteArticleOneTag(Integer articleId,String tagName);

    String resetArticleTag(ArticleTag tagBean);

    String batchResetArticleTag(ArticleTag tagBean);

    Integer createOneTag(ArticleTag tagBean);

    Integer createOneTag(String tagName);

    Integer createOneTagRelation(ArticleTag tagBean);

    Integer createOneTagRelation(Integer articleId, Integer tagId,String tagName);

    Integer deleteOneTag(Integer tagId);

    Integer deleteOneTagRelation(Integer tagId, Integer articleId);

    boolean checkTagExist(String tagName);

    boolean checkTagRelationExist(Integer articleId,Integer tagId);

    boolean checkArticleExist(Integer articleId);

    String deleteArticleAllTag(ArticleTag tagBean);

    Integer deleteArticleAllTag(Integer articleId);

    String batchDeleteArticleTag(ArticleTag tagBean);

    String batchDeleteArticleAllTag(ArticleTag tagBean);

    RegularRecommend getRegularRecommendArticle(Integer articleId);

    BlogInfo selectInfomation();
}
