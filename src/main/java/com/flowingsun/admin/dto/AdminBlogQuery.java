package com.flowingsun.admin.dto;

import com.flowingsun.article.entity.Category;
import com.flowingsun.common.utils.PageQueryBean;

import java.util.Date;

public class AdminBlogQuery extends PageQueryBean {
    /**
     * 文章ID
     */
    private Integer articleId;
    /**
     * 文章主分类ID
     */
    private Integer articleMid;
    /**
     * 文章二级分类ID
     */
    private Integer articleCid;
    /**
     * 文章标题
     */
    private String articlTitle;
    /**
     * 文章创建者Id
     */
    private Integer userId;
    /**
     * 文章创建时间
     */
    private Date createDate;
    /**
     * 文章修改时间
     */
    private Date editDate;
    /**
     * 分类选择
     */
    private Category categoryChoice;
    /**
     * 文章标签ID
     */
    private Integer tagId;
    /**
     * 文章-标签关系ID
     */
    private Integer tagRelationId;
    /**
     * 标签名
     */
    private String tagName;
    /**
     * 当前页数
     */
    private Integer pageNum;
    /**
     * 每页显示个数
     */
    private Integer pageSize;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTagRelationId() {
        return tagRelationId;
    }

    public void setTagRelationId(Integer tagRelationId) {
        this.tagRelationId = tagRelationId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    @Override
    public Integer getPageNum() {
        return super.getPageNum();
    }

    @Override
    public void setPageNum(Integer pageNum) {
        super.pageNum = pageNum;
    }

    @Override
    public Integer getPageSize() {
        return super.getPageSize();
    }

    @Override
    public void setPageSize(Integer pageSize) {
        super.pageSize = pageSize;
    }

    public Category getCategoryChoice() {
        return categoryChoice;
    }

    public void setCategoryChoice(Category categoryChoice) {
        this.categoryChoice = categoryChoice;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getArticleMid() {
        return articleMid;
    }

    public void setArticleMid(Integer articleMid) {
        this.articleMid = articleMid;
    }

    public Integer getArticleCid() {
        return articleCid;
    }

    public void setArticleCid(Integer articleCid) {
        this.articleCid = articleCid;
    }

    public String getArticlTitle() {
        return articlTitle;
    }

    public void setArticlTitle(String articlTitle) {
        this.articlTitle = articlTitle;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

}
