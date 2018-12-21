package com.flowingsun.user.entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private Long id;

    private String telephone;

    private String useremail;

    private String username;

    private String userpass;

    private String headImage;

    private String salt;

    private Integer userstatus;

    private List<Role> roleList;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(Integer userstatus) {
        this.userstatus = userstatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", telephone='" + telephone + '\'' +
                ", useremail='" + useremail + '\'' +
                ", username='" + username + '\'' +
                ", userpass='" + userpass + '\'' +
                ", headImage='" + headImage + '\'' +
                ", salt='" + salt + '\'' +
                ", userstatus=" + userstatus +
                ", roleList=" + roleList +
                '}';
    }
}