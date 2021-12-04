package com.wangfugui.activiti.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfugui.activiti.dao.LeaveVo;
import com.wangfugui.activiti.dao.Leaves;
import com.wangfugui.activiti.dao.dto.HandleDto;
import com.wangfugui.activiti.dao.dto.UpcomingDto;
import com.wangfugui.activiti.dao.mapper.LeaveMapper;
import com.wangfugui.activiti.service.LeaveService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/2
 * @since JDK 1.8.0
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leaves> implements LeaveService {

    @Autowired
    private RuntimeService runtimeservice;
    @Autowired
    private IdentityService identityservice;
    @Autowired
    private TaskService taskservice;
    @Autowired
    private HistoryService historyService;


    /**
     * 开启一个流程
     *
     * @param leave
     * @Param: [leave]
     * @return: org.activiti.engine.runtime.ProcessInstance
     * @Author: MaSiyi
     * @Date: 2021/12/3
     */
    @Override
    public String startLeave(Leaves leave) {
        leave.setId(UUID.randomUUID().toString().substring(0, 8));
        //设置用户
        identityservice.setAuthenticatedUserId(leave.getUserId());
        //使用leaveapply表的主键作为businesskey,连接业务数据和流程数据
        String businesskey = String.valueOf(leave.getId());
        //发起请假流程
        Map<String, Object> map = new HashMap<>();
        //下个步骤处理人
        map.put("deptleader", "Tom");
        ProcessInstance processInstance = runtimeservice.startProcessInstanceByKey("leave", businesskey, map);
        String instanceid = processInstance.getId();
        //关联流程表
        leave.setProcessInstanceId(instanceid);
        this.save(leave);
        return "申请成功";
    }

    /**
     * 获取待办列表
     *
     * @param upcomingDto
     * @Param: [upcomingDto]
     * @return: java.util.List<com.wangfugui.activiti.dao.Leaves>
     * @Author: MaSiyi
     * @Date: 2021/12/3
     */
    @Override
    public List<LeaveVo> upcoming(UpcomingDto upcomingDto) {
        List<LeaveVo> leaves = new ArrayList<>();
        // 使用任务候选人查询待办列表
        List<Task> tasks = taskservice.createTaskQuery().taskAssignee(upcomingDto.getUsername()).taskName("部门领导审批")
                .listPage(upcomingDto.getPage(), upcomingDto.getSize());
        for (Task task : tasks) {
            String instanceId = task.getProcessInstanceId();
            //查询正在运行的task
            ProcessInstance processInstance = runtimeservice.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            Leaves leave = this.getById(businessKey);
            LeaveVo leaveVo = new LeaveVo();
            BeanUtils.copyProperties(leave,leaveVo);
            leaveVo.setTaskId(task.getId());
            leaves.add(leaveVo);
        }
        return leaves;
    }

    /**
     * 审批任务

     * @Param: [taskid]
     * @return: java.lang.String
     * @Author: MaSiyi
     * @Date: 2021/12/3
     */
    @Override
    public String handle(HandleDto handleDto) {
        //完成指定任务
        taskservice.claim(handleDto.getTaskId(), handleDto.getUserName());
        //进行下一个任务
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("deptleaderapprove", handleDto.getApprove());
        variables.put("hr", handleDto.getNextUserNam());
        taskservice.complete(handleDto.getTaskId(), variables);
        return "处理成功";
    }

    /**
     * 我发起的请假流程
     *
     * @Param: []
     * @return: java.util.List<com.wangfugui.activiti.dao.LeaveVo>
     * @Author: MaSiyi
     * @Date: 2021/12/4
     * @param userId
     */
    @Override
    public List<LeaveVo> myleave(String userId) {
        QueryWrapper<Leaves> leavesQueryWrapper = new QueryWrapper<>();
        leavesQueryWrapper.lambda().eq(Leaves::getUserId, userId);
        //从请假表中获取数据
        List<Leaves> list = this.list(leavesQueryWrapper);
        ArrayList<LeaveVo> leaveVos = new ArrayList<>();
        for (Leaves leaves : list) {
            //获取当前流程转态
            ProcessInstance process = runtimeservice.createProcessInstanceQuery().
                    processInstanceId(leaves.getProcessInstanceId()).singleResult();
            LeaveVo leaveVo = new LeaveVo();
            BeanUtils.copyProperties(leaves, leaveVo);
            if (process == null) {
                leaveVo.setStatus("已结束");
            } else {
                leaveVo.setStatus("运行中");
            }
            leaveVos.add(leaveVo);

        }
        return leaveVos;
    }

    /** 获取历史流程信息
     * @Param: [procInstId]
     * @return: org.activiti.engine.history.HistoricProcessInstance
     * @Author: MaSiyi
     * @Date: 2021/12/4
     */
    @Override
    public HistoricProcessInstance getHiProcByProcInstId(String procInstId) {
        if (ObjectUtils.isEmpty(procInstId)) {
            return null;
        }
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        //设置当前用户参数
        query.processInstanceId(procInstId);
        //排序
        query.orderByProcessInstanceStartTime().desc();

        return query.singleResult();
    }

    /** 获取历史任务
     * @Param: [procKey, businessID]
     * @return: org.activiti.engine.history.HistoricProcessInstance
     * @Author: MaSiyi
     * @Date: 2021/12/4
     */
    @Override
    public HistoricProcessInstance getHiProcByProcKeyAndBusinessID(String procKey, String businessID) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        //设置当前用户参数
        query.processDefinitionKey(procKey);
        query.processInstanceBusinessKey(businessID);
        //排序
        query.orderByProcessInstanceStartTime().desc();
        //
        List<HistoricProcessInstance> historicProcessInstanceList = query.list();
        if(historicProcessInstanceList == null || historicProcessInstanceList.size() == 0){
            return null;
        } else if(historicProcessInstanceList.size() > 1){
            for(int i=0;i<historicProcessInstanceList.size();i++){
                HistoricProcessInstance historicProcessInstance = historicProcessInstanceList.get(i);
                if(historicProcessInstance.getEndTime() == null){
                    return historicProcessInstance;
                }
            }
        }
        return historicProcessInstanceList.get(0);
    }

    /**
     * 根据taskid获取历史任务
     *
     * @param taskId
     * @return
     */
    @Override
    public HistoricTaskInstance getTaskById(String taskId)  {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        query.taskId(taskId);
        //排序
        query.orderByTaskCreateTime().desc();
        return query.singleResult();
    }

}
