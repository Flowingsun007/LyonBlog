package com.flowingsun.article.entity;

import java.util.Date;

public class BlogInfo {
    private String viewCount;
    private String visitorCount;
    private String userCount;
    private String articleCount;
    private String commentCount;
    private String thankCount;

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(String visitorCount) {
        this.visitorCount = visitorCount;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(String articleCount) {
        this.articleCount = articleCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getThankCount() {
        return thankCount;
    }

    public void setThankCount(String thankCount) {
        this.thankCount = thankCount;
    }
}
