package com.wangfugui.activiti.dao.dto;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/3
 * @since JDK 1.8.0
 */
public class UpcomingDto {
    private Integer page;
    private Integer size;
    private String username;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
