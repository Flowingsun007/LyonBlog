package com.flowingsun.behavior.entity;

import java.util.Date;

public class Thank {
    private Integer id;

    private Integer articleid;

    private Long userid;

    private Date thankdate;

    private byte thankStatus;

    public byte getThankStatus() {
        return thankStatus;
    }

    public void setThankStatus(byte thankStatus) {
        this.thankStatus = thankStatus;
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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getThankdate() {
        return thankdate;
    }

    public void setThankdate(Date thankdate) {
        this.thankdate = thankdate;
    }
}