package com.wangfugui.activiti.controller;

import com.wangfugui.activiti.dao.LeaveDO;
import com.wangfugui.activiti.service.LeaveService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/2
 * @since JDK 1.8.0
 */
@RestController
@RequestMapping("/leave")
public class LeaveController extends BaseController<LeaveService, LeaveDO>{


}
