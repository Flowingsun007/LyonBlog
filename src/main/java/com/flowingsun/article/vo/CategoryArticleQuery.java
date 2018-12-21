package com.flowingsun.article.vo;

import com.flowingsun.common.utils.PageQueryBean;

public class CategoryArticleQuery extends PageQueryBean {
    /**
     * 文章ID
     */
    private Integer articleId;
    /**
     * 主分类ID
     */
    private Integer mId;
    /**
     * 二级分类ID
     */
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
