package com.flowingsun.behavior.entity;

import com.flowingsun.behavior.vo.PictureQuery;

import java.util.Date;
import java.util.List;

public class Picture {
    private Integer id;

    private Long userid;

    private Date createdate;

    private String filepath;

    private String details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", userid=" + userid +
                ", createdate=" + createdate +
                ", filepath='" + filepath + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}