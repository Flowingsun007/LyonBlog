package com.flowingsun.user.dto;

import com.flowingsun.user.entity.User;

import java.util.List;

/**
 * @author Lyon
 * @date 2019/1/19 15:51
 * @description UserDto
 **/
public class UserDto {

    private User user;

    private List<String> displayImages;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getDisplayImages() {
        return displayImages;
    }

    public void setDisplayImages(List<String> displayImages) {
        this.displayImages = displayImages;
    }

}
