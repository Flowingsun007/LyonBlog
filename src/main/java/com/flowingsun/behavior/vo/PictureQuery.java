package com.flowingsun.behavior.vo;

import com.flowingsun.behavior.entity.Picture;
import com.flowingsun.common.utils.PageQueryBean;

import java.util.Date;
import java.util.List;

public class PictureQuery extends PageQueryBean{

    private Long userid;

    private String username;

    private Date createDate;

    private Date startDate ;

    private Date endDate ;

    private Date rangeDate;

    private List<Picture> pictureList;

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRangeDate() {
        return rangeDate;
    }

    public void setRangeDate(Date rangeDate) {
        this.rangeDate = rangeDate;
    }

    @Override
    public String toString() {
        return "PictureQuery{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", createDate=" + createDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", rangeDate=" + rangeDate +
                ", pictureList=" + pictureList +
                '}';
    }
}
