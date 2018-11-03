package com.flowingsun.behavior.entity;


import java.util.List;

public class BehaviorStatus {

    private Long userid;

    private byte viewStatus;

    private byte thankStatus;

    private byte collectStatus;

    private byte commentStatus;

    private byte commentLikeStatus;

    private byte commentDiscussStatus;

    private int viewCount;

    private int thankCount;

    private int collectionCount;

    private int commentCount;

    private int commentLikeCount;

    private int commentDiscussCount;

    private List<Collection> collectionList;

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

    public byte getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(byte collectStatus) {
        this.collectStatus = collectStatus;
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

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getThankCount() {
        return thankCount;
    }

    public void setThankCount(int thankCount) {
        this.thankCount = thankCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCommentLikeCount() {
        return commentLikeCount;
    }

    public void setCommentLikeCount(int commentLikeCount) {
        this.commentLikeCount = commentLikeCount;
    }

    public int getCommentDiscussCount() {
        return commentDiscussCount;
    }

    public void setCommentDiscussCount(int commentDiscussCount) {
        this.commentDiscussCount = commentDiscussCount;
    }

    public List<Collection> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<Collection> collectionList) {
        this.collectionList = collectionList;
    }

}
