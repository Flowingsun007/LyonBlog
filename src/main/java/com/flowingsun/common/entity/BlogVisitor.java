package com.flowingsun.common.entity;

import java.util.Date;

public class BlogVisitor {
    private Long id;

    private Long userid;

    private Integer articleid;

    private String os;

    private String browser;

    private String ip;

    private String sourceurl;

    private String targeturl;

    private Date datetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os == null ? null : os.trim();
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser == null ? null : browser.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl == null ? null : sourceurl.trim();
    }

    public String getTargeturl() {
        return targeturl;
    }

    public void setTargeturl(String targeturl) {
        this.targeturl = targeturl == null ? null : targeturl.trim();
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "BlogVisitor{" +
                "id=" + id +
                ", userid=" + userid +
                ", articleid=" + articleid +
                ", os='" + os + '\'' +
                ", browser='" + browser + '\'' +
                ", ip='" + ip + '\'' +
                ", sourceurl='" + sourceurl + '\'' +
                ", targeturl='" + targeturl + '\'' +
                ", datetime=" + datetime +
                '}';
    }
}