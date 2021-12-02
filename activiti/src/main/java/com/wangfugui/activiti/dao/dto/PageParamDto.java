package com.wangfugui.activiti.dao.dto;


/**
 * @author MaSiyi
 * @version 1.0.0 2021/11/26
 * @since JDK 1.8.0
 */
public class PageParamDto<T> {
    private Integer page;
    private Integer size;
    private String asc;
    private String desc;
    /**
     * 实体类参数
     */
    private T params;

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

    public String getAsc() {
        return asc;
    }

    public void setAsc(String asc) {
        this.asc = asc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}
