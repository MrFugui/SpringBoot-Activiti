package com.wangfugui.activiti.dao;



import java.io.Serializable;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/3
 * @since JDK 1.8.0
 */
public class LeaveVo extends Leaves implements Serializable {

    String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
