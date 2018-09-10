package com.flowingsun.article.entity;

import java.util.Date;

public class ArticleThank {
    private Integer articleId;
    private Integer thankNum;
    private Integer userId;
    private String userName;
    private Date thankDate;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getThankNum() {
        return thankNum;
    }

    public void setThankNum(Integer thankNum) {
        this.thankNum = thankNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getThankDate() {
        return thankDate;
    }

    public void setThankDate(Date thankDate) {
        this.thankDate = thankDate;
    }
}
