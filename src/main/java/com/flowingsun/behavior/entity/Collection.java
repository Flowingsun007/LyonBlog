package com.flowingsun.behavior.entity;

import java.util.Date;

public class Collection {

    private Integer id;

    private Integer articleid;

    private String articleTitle;

    private Long userid;

    private Date collectdate;

    private byte collectStatus;

    public byte getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(byte collectStatus) {
        this.collectStatus = collectStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCollectdate() {
        return collectdate;
    }

    public void setCollectdate(Date collectdate) {
        this.collectdate = collectdate;
    }

}