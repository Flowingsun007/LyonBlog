package com.flowingsun.article.vo;

import com.flowingsun.common.utils.PageQueryBean;

public class TagArticleQuery extends PageQueryBean {
    /**
     * 文章标签ID
     */
    private Integer tagid;
    /**
     * 标签名
     */
    private Integer tagname;

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Integer getTagname() {
        return tagname;
    }

    public void setTagname(Integer tagname) {
        this.tagname = tagname;
    }
}
