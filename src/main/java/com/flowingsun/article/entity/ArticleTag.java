package com.flowingsun.article.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ArticleTag implements Serializable {

    private Integer articleId;

    private Integer tagId;

    private String tagName;

    private Date createDate;

    private Map<Integer,String> tagMap;

    private String articleIdStr;

    private String articleTagsStr;

    public String getArticleIdStr() {
        return articleIdStr;
    }

    public void setArticleIdStr(String articleIdStr) {
        this.articleIdStr = articleIdStr;
    }

    public String getArticleTagsStr() {
        return articleTagsStr;
    }

    public void setArticleTagsStr(String articleTagsStr) {
        this.articleTagsStr = articleTagsStr;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Map<Integer, String> getTagMap() {
        return tagMap;
    }

    public void setTagMap(Map<Integer, String> tagMap) {
        this.tagMap = tagMap;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ArticleTag{" +
                "articleId=" + articleId +
                ", tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", createDate=" + createDate +
                ", tagMap=" + tagMap +
                ", articleIdStr='" + articleIdStr + '\'' +
                ", articleTagsStr='" + articleTagsStr + '\'' +
                '}';
    }
}
