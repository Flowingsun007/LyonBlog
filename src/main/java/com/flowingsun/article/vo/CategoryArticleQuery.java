package com.flowingsun.article.vo;

import com.flowingsun.common.utils.PageQueryBean;

public class CategoryArticleQuery extends PageQueryBean {
    private Integer articleId;
    private Integer mId;
    private Integer cId;


    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    @Override
    public String toString() {
        return "CategoryArticleQuery{" +
                "articleId=" + articleId +
                ", mId=" + mId +
                ", cId=" + cId +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
