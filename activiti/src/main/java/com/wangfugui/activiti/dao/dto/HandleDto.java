package com.wangfugui.activiti.dao.dto;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/3
 * @since JDK 1.8.0
 */
public class HandleDto {

    String taskId;

    String userName;

    String nextUserNam;

    String approve;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNextUserNam() {
        return nextUserNam;
    }

    public void setNextUserNam(String nextUserNam) {
        this.nextUserNam = nextUserNam;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }
}
