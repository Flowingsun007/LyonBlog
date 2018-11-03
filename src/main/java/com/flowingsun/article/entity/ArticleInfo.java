package com.flowingsun.article.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ArticleInfo {
    private Integer articleId;

    private Integer userid;

    private String articleTitle;

    private String articleSubtitle;

    private Date createDate;

    private Date editDate;

    private Integer articleSecondId;

    private Integer articleMainId;

    private String articleMainName;

    private String articleSecondName;

    private Integer articleComment;

    private Integer articleThank;

    private Integer articleCollection;

    private int tagId;

    private String tagName;

    private List<String> articleTags;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleSubtitle() {
        return articleSubtitle;
    }

    public void setArticleSubtitle(String articleSubtitle) {
        this.articleSubtitle = articleSubtitle;
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

    public Integer getArticleSecondId() {
        return articleSecondId;
    }

    public void setArticleSecondId(Integer articleSecondId) {
        this.articleSecondId = articleSecondId;
    }

    public Integer getArticleMainId() {
        return articleMainId;
    }

    public void setArticleMainId(Integer articleMainId) {
        this.articleMainId = articleMainId;
    }

    public String getArticleMainName() {
        return articleMainName;
    }

    public void setArticleMainName(String articleMainName) {
        this.articleMainName = articleMainName;
    }

    public String getArticleSecondName() {
        return articleSecondName;
    }

    public void setArticleSecondName(String articleSecondName) {
        this.articleSecondName = articleSecondName;
    }

    public Integer getArticleComment() {
        return articleComment;
    }

    public void setArticleComment(Integer articleComment) {
        this.articleComment = articleComment;
    }

    public Integer getArticleThank() {
        return articleThank;
    }

    public void setArticleThank(Integer articleThank) {
        this.articleThank = articleThank;
    }

    public Integer getArticleCollection() {
        return articleCollection;
    }

    public void setArticleCollection(Integer articleCollection) {
        this.articleCollection = articleCollection;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<String> getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(List<String> articleTags) {
        this.articleTags = articleTags;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "articleId=" + articleId +
                ", userid=" + userid +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleSubtitle='" + articleSubtitle + '\'' +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                ", articleSecondId=" + articleSecondId +
                ", articleMainId=" + articleMainId +
                ", articleMainName='" + articleMainName + '\'' +
                ", articleSecondName='" + articleSecondName + '\'' +
                ", articleComment=" + articleComment +
                ", articleThank=" + articleThank +
                ", tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", articleTags=" + articleTags +
                '}';
    }
}
