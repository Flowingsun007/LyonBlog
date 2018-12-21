package com.flowingsun.behavior.entity;

import java.util.Date;

public class Discussion {
    private Integer id;

    private Integer articleid;

    private Long mainUserid;

    private String mainUsername;

    private Integer mainCommentid;

    private Long userid;

    private String userName;

    private String discussion;

    private Date discussdate;

    private byte discussionStatus;

    public byte getDiscussionStatus() {
        return discussionStatus;
    }

    public void setDiscussionStatus(byte discussionStatus) {
        this.discussionStatus = discussionStatus;
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

    public Long getMainUserid() {
        return mainUserid;
    }

    public void setMainUserid(Long mainUserid) {
        this.mainUserid = mainUserid;
    }

    public String getMainUsername() {
        return mainUsername;
    }

    public void setMainUsername(String mainUsername) {
        this.mainUsername = mainUsername;
    }

    public Integer getMainCommentid() {
        return mainCommentid;
    }

    public void setMainCommentid(Integer mainCommentid) {
        this.mainCommentid = mainCommentid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion == null ? null : discussion.trim();
    }

    public Date getDiscussdate() {
        return discussdate;
    }

    public void setDiscussdate(Date discussdate) {
        this.discussdate = discussdate;
    }
}