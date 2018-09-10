package com.flowingsun.behavior.entity;


public class BehaviorStatus {

    private Long userid;

    private byte viewStatus;

    private byte thankStatus;

    private byte commentStatus;

    private byte commentLikeStatus;

    private byte commentDiscussStatus;

    public byte getCommentDiscussStatus() {
        return commentDiscussStatus;
    }

    public void setCommentDiscussStatus(byte commentDiscussStatus) {
        this.commentDiscussStatus = commentDiscussStatus;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public byte getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(byte viewStatus) {
        this.viewStatus = viewStatus;
    }

    public byte getThankStatus() {
        return thankStatus;
    }

    public void setThankStatus(byte thankStatus) {
        this.thankStatus = thankStatus;
    }

    public byte getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(byte commentStatus) {
        this.commentStatus = commentStatus;
    }

    public byte getCommentLikeStatus() {
        return commentLikeStatus;
    }

    public void setCommentLikeStatus(byte commentLikeStatus) {
        this.commentLikeStatus = commentLikeStatus;
    }

}
