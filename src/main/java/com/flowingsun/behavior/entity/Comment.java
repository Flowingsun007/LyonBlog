package com.flowingsun.behavior.entity;

import java.util.Date;
import java.util.List;

public class Comment {
    private Integer id;

    private Integer articleid;

    private Long userid;

    private String userName;

    private String comment;

    private Date commentdate;

    private Integer commentLikeNum;

    private List<Discussion> discussionList;

    private byte commentStatus;

    public byte getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(byte commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getCommentLikeNum() {
        return commentLikeNum;
    }

    public void setCommentLikeNum(Integer commentLikeNum) {
        this.commentLikeNum = commentLikeNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Discussion> getDiscussionList() {
        return discussionList;
    }

    public void setDiscussionList(List<Discussion> discussionList) {
        this.discussionList = discussionList;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(Date commentdate) {
        this.commentdate = commentdate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleid=" + articleid +
                ", userid=" + userid +
                ", userName='" + userName + '\'' +
                ", comment='" + comment + '\'' +
                ", commentdate=" + commentdate +
                ", commentLikeNum=" + commentLikeNum +
                ", discussionList=" + discussionList +
                '}';
    }
}