package com.flowingsun.admin.entity;

import com.flowingsun.article.entity.Category;
import com.flowingsun.common.utils.PageQueryBean;

import java.util.Date;

public class AdminBlogQuery extends PageQueryBean {
    private Integer articleId;
    private Integer articleMid;
    private Integer articleCid;
    private String articlTitle;
    private Date createDate;
    private Date editDate;
    private Category categoryChoice;
    private Integer tagId;
    private Integer tagRelationId;
    private String tagName;
    private Integer pageNum;
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

    @Override
    public String toString() {
        return "AdminBlogQuery{" +
                "articleId=" + articleId +
                ", articleMid=" + articleMid +
                ", articleCid=" + articleCid +
                ", articlTitle='" + articlTitle + '\'' +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                ", categoryChoice=" + categoryChoice +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
