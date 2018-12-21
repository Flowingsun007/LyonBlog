package com.flowingsun.behavior.entity;

import java.util.Date;

public class CommentLike {
    private Integer id;

    private Integer articleid;

    private Long mainuserid;

    private Integer commentid;

    private Long userid;

    private Date likedate;

    private byte commentLikeStatus;

    public byte getCommentLikeStatus() {
        return commentLikeStatus;
    }

    public void setCommentLikeStatus(byte commentLikeStatus) {
        this.commentLikeStatus = commentLikeStatus;
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

    public Long getMainuserid() {
        return mainuserid;
    }

    public void setMainuserid(Long mainuserid) {
        this.mainuserid = mainuserid;
    }

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getLikedate() {
        return likedate;
    }

    public void setLikedate(Date likedate) {
        this.likedate = likedate;
    }
}