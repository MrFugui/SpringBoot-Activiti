package com.wangfugui.activiti.dao;

import java.util.Date;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/2
 * @since JDK 1.8.0
 */

public class Leaves {

    private String id;
    private String processInstanceId;
    private String userId;
    private String startTime;
    private String endTime;
    private String leaveType;
    private String reason;
    private Date applyTime;
    private String realityStartTime;
    private String realityEndTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getRealityStartTime() {
        return realityStartTime;
    }

    public void setRealityStartTime(String realityStartTime) {
        this.realityStartTime = realityStartTime;
    }

    public String getRealityEndTime() {
        return realityEndTime;
    }

    public void setRealityEndTime(String realityEndTime) {
        this.realityEndTime = realityEndTime;
    }
}
