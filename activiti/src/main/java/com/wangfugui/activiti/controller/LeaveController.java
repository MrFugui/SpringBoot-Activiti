package com.wangfugui.activiti.controller;

import com.wangfugui.activiti.dao.LeaveVo;
import com.wangfugui.activiti.dao.Leaves;
import com.wangfugui.activiti.dao.dto.HandleDto;
import com.wangfugui.activiti.dao.dto.UpcomingDto;
import com.wangfugui.activiti.service.LeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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



}
