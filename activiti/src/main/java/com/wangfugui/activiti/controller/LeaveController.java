package com.wangfugui.activiti.controller;

import com.wangfugui.activiti.dao.LeaveVo;
import com.wangfugui.activiti.dao.Leaves;
import com.wangfugui.activiti.dao.dto.HandleDto;
import com.wangfugui.activiti.dao.dto.UpcomingDto;
import com.wangfugui.activiti.service.LeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/2
 * @since JDK 1.8.0
 */
@RestController
@RequestMapping("/leave")
@Api(tags = "请假")
public class LeaveController {

    @Autowired
    protected LeaveService leaveService;

    @PostMapping("/startLeave")
    @ApiOperation("发起一个请假流程")
    public String startLeave(@RequestBody Leaves leave) {
        return leaveService.startLeave(leave);
    }

    @PostMapping("/upcoming")
    @ApiOperation("获取待办列表")
    public List<LeaveVo> listTask(@RequestBody UpcomingDto upcomingDto) {
        return leaveService.upcoming(upcomingDto);
    }

    @PostMapping("/handle")
    @ApiOperation("处理流程")
    public String handle(@RequestBody HandleDto handleDto) {
        return leaveService.handle(handleDto);
    }

    @GetMapping("/myleave")
    @ApiOperation("我发起的请假流程")
    public List<LeaveVo> myleave(@RequestParam String userId) {
        return leaveService.myleave(userId);
    }

    @GetMapping("/getHistory")
    @ApiOperation("获取历史流程信息")
    public HistoricProcessInstance getHistory(@RequestParam String procInstId) {
        return leaveService.getHiProcByProcInstId(procInstId);
    }

    @GetMapping("/getHiProcByProcKeyAndBusinessID")
    @ApiOperation("获取历史任务")
    public HistoricProcessInstance getHiProcByProcKeyAndBusinessID(@RequestParam String procKey, @RequestParam String businessID) {
        return leaveService.getHiProcByProcKeyAndBusinessID(procKey, businessID);
    }

    @GetMapping("/getTaskById")
    @ApiOperation("根据taskid获取历史任务")
    public HistoricTaskInstance getTaskById(@RequestParam String taskId) {
        return leaveService.getTaskById(taskId);
    }


}
