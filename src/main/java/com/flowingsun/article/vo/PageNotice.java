package com.flowingsun.article.vo;
/**
 *@Author Lyon[flowingsun007@163.com]
 *@Date 18/06/6 20:27
 *@Description 用于用户登录注册等页面状态，信息的显示和通知
 */
public class PageNotice {
    private Integer status;
    private String notice;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return "PageNotice{" +
                "status=" + status +
                ", notice='" + notice + '\'' +
                '}';
    }
}
