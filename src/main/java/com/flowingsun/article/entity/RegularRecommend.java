package com.flowingsun.article.entity;

public class RegularRecommend {
    private Integer articleId;
    private Integer previousArticleId;
    private Integer nextArticleId;
    private String previousTitle;
    private String nextTitle;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getPreviousArticleId() {
        return previousArticleId;
    }

    public void setPreviousArticleId(Integer previousArticleId) {
        this.previousArticleId = previousArticleId;
    }

    public Integer getNextArticleId() {
        return nextArticleId;
    }

    public void setNextArticleId(Integer nextArticleId) {
        this.nextArticleId = nextArticleId;
    }

    public String getPreviousTitle() {
        return previousTitle;
    }

    public void setPreviousTitle(String previousTitle) {
        this.previousTitle = previousTitle;
    }

    public String getNextTitle() {
        return nextTitle;
    }

    public void setNextTitle(String nextTitle) {
        this.nextTitle = nextTitle;
    }

    @Override
    public String toString() {
        return "RegularRecommend{" +
                "previousArticleId=" + previousArticleId +
                ", nextArticleId=" + nextArticleId +
                ", previousTitle='" + previousTitle + '\'' +
                ", nextTitle='" + nextTitle + '\'' +
                '}';
    }
}
