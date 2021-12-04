package com.wangfugui.activiti.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfugui.activiti.dao.LeaveVo;
import com.wangfugui.activiti.dao.Leaves;
import com.wangfugui.activiti.dao.dto.HandleDto;
import com.wangfugui.activiti.dao.dto.UpcomingDto;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;

import java.util.List;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/2
 * @since JDK 1.8.0
 */
public interface LeaveService extends IService<Leaves> {
    /**
     * 开启一个流程
     *
     * @param leaveDO
     * @Param: [leaveDO]
     * @return: org.activiti.engine.runtime.ProcessInstance
     * @Author: MaSiyi
     * @Date: 2021/12/3
     */
    String startLeave(Leaves leaveDO);


    /**
     * 获取待办列表
     *
     * @Param: [upcomingDto]
     * @return: java.util.List<com.wangfugui.activiti.dao.Leaves>
     * @Author: MaSiyi
     * @Date: 2021/12/3
     */
    List<LeaveVo> upcoming(UpcomingDto upcomingDto);

    /**
     * 审批任务
     *
     * @Param: [taskid]
     * @return: java.lang.String
     * @Author: MaSiyi
     * @Date: 2021/12/3
     */
    String handle(HandleDto handleDto);


    /** 我发起的请假流程
     * @Param: []
     * @return: java.util.List<com.wangfugui.activiti.dao.LeaveVo>
     * @Author: MaSiyi
     * @Date: 2021/12/4
     * @param userId
     */
    List<LeaveVo> myleave(String userId);

    /** 获取历史流程信息
     * @Param: [procInstId]
     * @return: org.activiti.engine.history.HistoricProcessInstance
     * @Author: MaSiyi
     * @Date: 2021/12/4
     */
    HistoricProcessInstance getHiProcByProcInstId(String procInstId);

    /** 获取历史任务
     * @Param: [procKey, businessID]
     * @return: org.activiti.engine.history.HistoricProcessInstance
     * @Author: MaSiyi
     * @Date: 2021/12/4
     */
    HistoricProcessInstance getHiProcByProcKeyAndBusinessID(String procKey, String businessID);

    /**
     * @Param: [taskId]
     * @return: org.activiti.engine.history.HistoricTaskInstance
     * @Author: MaSiyi
     * @Date: 2021/12/4
     */
    HistoricTaskInstance getTaskById(String taskId);
}
