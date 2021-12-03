## 啦啦啦啦啦，富贵同学又开始开坑了，出了个免费的专栏，主要给大家从0基础开始用springBoot集成第三方的插件或者功能，如果这篇专栏能帮到你，一定不要忘了点一个赞哦！！欢迎大家收藏分享
![在这里插入图片描述](https://img-blog.csdnimg.cn/385dc942abfc4a019d845055328814c1.png#pic_center)
## 第一步，导入jar包

```java
		  <dependency>
            <groupId>org.activiti</groupId>
            <artifactId>activiti-spring-boot-starter-basic</artifactId>
            <version>5.22.0</version>
        </dependency>
```
这是activiti的核心包，我们还添加其他的jar包

```java
		  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.30</version>
        </dependency>
```
## 第二步，编写配置文件

```java
server:
  port: 8089
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/activiti
    driver-class-name: com.mysql.jdbc.Driver
    username: root
    password: 123456
```
## 第三步，创建activiti库
![在这里插入图片描述](https://img-blog.csdnimg.cn/c1e5a2c049834f8294b8934e335cb638.png)

## 第四步，运行启动类
![在这里插入图片描述](https://img-blog.csdnimg.cn/4b3fd9ef3d8247b4ada367b2becfbd90.png)
好，遇到这个时候就出现问题了，这是富贵同学替大家踩坑了，大家记得避开
**启动报错Could not find class [org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration]**
这个问题是activiti里面本身会依赖Security，所以我们将他排除，我们在启动类上加上

```java
exclude = SecurityAutoConfiguration.class
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/f933930881cf4f7fbfc3f2bbe59c88f7.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_18,color_FFFFFF,t_70,g_se,x_16)
好的，我们启动，这个时候又碰见另外的一个问题
**Caused by: java.io.FileNotFoundException: class path resource [processes/] cannot be resolved to URL because it does not exist**
这个是因为activiti在启动的时候会去找bpm文件，会去到默认的processes文件夹中找，
这个时候我们在配置类中加入

```java
  activiti:
    check-process-definitions: false
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/3fe6c68b88894df7b53e58b2bc16c122.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_14,color_FFFFFF,t_70,g_se,x_16)
这个时候就启动成功啦！
![在这里插入图片描述](https://img-blog.csdnimg.cn/53fcb552cb2b404ba4b3ce748c4f38bb.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
## 第五步.启动成功之后查看我们的数据库
![在这里插入图片描述](https://img-blog.csdnimg.cn/7297ae6bcaf54c06b47d2b0bc51e388c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
就会发现25张表已经自动生成了！
如果没有看过富贵同学的第一篇请移步到这个地址查看[SpringBoot集成Activiti（一）](https://blog.csdn.net/csdnerM/article/details/121680351)
第一步，取消yml的配置，因为我们需要创建一个流程
![在这里插入图片描述](https://img-blog.csdnimg.cn/39c476f9da674089b62e16b5dc0f60d4.png)
`leave.bpmn`文件感谢(https://gitee.com/shenzhanwang/Spring-activiti)提供的流程文件

```java
<?xml version="1.0" encoding="UTF-8"?>
<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:activiti="http://activiti.org/bpmn" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" typeLanguage="http://www.w3.org/2001/XMLSchema" expressionLanguage="http://www.w3.org/1999/XPath" targetNamespace="http://www.activiti.org/test">
  <process id="leave" name="My process" isExecutable="true">
    <userTask id="deptleaderaudit" name="部门领导审批" activiti:assignee="${deptleader}" activiti:candidateGroups="部门经理"></userTask>
    <exclusiveGateway id="exclusivegateway1" name="Exclusive Gateway"></exclusiveGateway>
    <userTask id="hraudit" name="人事审批" activiti:assignee="${hr}" activiti:candidateGroups="人事"></userTask>
    <sequenceFlow id="flow3" name="同意" sourceRef="exclusivegateway1" targetRef="hraudit">
      <conditionExpression xsi:type="tFormalExpression"><![CDATA[${deptleaderapprove=='true'}]]></conditionExpression>
    </sequenceFlow>
    <userTask id="modifyapply" name="调整申请" activiti:assignee="${applyuserid}"></userTask>
    <sequenceFlow id="flow4" name="拒绝" sourceRef="exclusivegateway1" targetRef="modifyapply">
      <conditionExpression xsi:type="tFormalExpression"><![CDATA[${deptleaderapprove=='false'}]]></conditionExpression>
    </sequenceFlow>
    <sequenceFlow id="flow6" sourceRef="deptleaderaudit" targetRef="exclusivegateway1"></sequenceFlow>
    <exclusiveGateway id="exclusivegateway2" name="Exclusive Gateway"></exclusiveGateway>
    <sequenceFlow id="flow7" sourceRef="modifyapply" targetRef="exclusivegateway2"></sequenceFlow>
    <sequenceFlow id="flow8" name="重新申请" sourceRef="exclusivegateway2" targetRef="deptleaderaudit">
      <conditionExpression xsi:type="tFormalExpression"><![CDATA[${reapply=='true'}]]></conditionExpression>
    </sequenceFlow>
    <endEvent id="endevent1" name="End"></endEvent>
    <sequenceFlow id="flow9" name="结束流程" sourceRef="exclusivegateway2" targetRef="endevent1">
      <conditionExpression xsi:type="tFormalExpression"><![CDATA[${reapply=='false'}]]></conditionExpression>
    </sequenceFlow>
    <exclusiveGateway id="exclusivegateway3" name="Exclusive Gateway"></exclusiveGateway>
    <sequenceFlow id="flow10" sourceRef="hraudit" targetRef="exclusivegateway3"></sequenceFlow>
    <sequenceFlow id="flow11" name="拒绝" sourceRef="exclusivegateway3" targetRef="modifyapply">
      <conditionExpression xsi:type="tFormalExpression"><![CDATA[${hrapprove=='false'}]]></conditionExpression>
    </sequenceFlow>
    <userTask id="reportback" name="销假" activiti:assignee="${applyuserid}"></userTask>
    <sequenceFlow id="flow12" name="同意" sourceRef="exclusivegateway3" targetRef="reportback">
      <conditionExpression xsi:type="tFormalExpression"><![CDATA[${hrapprove=='true'}]]></conditionExpression>
    </sequenceFlow>
    <sequenceFlow id="flow13" sourceRef="reportback" targetRef="endevent1"></sequenceFlow>
    <startEvent id="startevent1" name="Start" activiti:initiator="${applyuserid}"></startEvent>
    <sequenceFlow id="flow14" sourceRef="startevent1" targetRef="deptleaderaudit"></sequenceFlow>
  </process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_leave">
    <bpmndi:BPMNPlane bpmnElement="leave" id="BPMNPlane_leave">
      <bpmndi:BPMNShape bpmnElement="deptleaderaudit" id="BPMNShape_deptleaderaudit">
        <omgdc:Bounds height="55.0" width="105.0" x="250.0" y="220.0"></omgdc:Bounds>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="exclusivegateway1" id="BPMNShape_exclusivegateway1">
        <omgdc:Bounds height="40.0" width="40.0" x="535.0" y="227.0"></omgdc:Bounds>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="hraudit" id="BPMNShape_hraudit">
        <omgdc:Bounds height="55.0" width="105.0" x="620.0" y="220.0"></omgdc:Bounds>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="modifyapply" id="BPMNShape_modifyapply">
        <omgdc:Bounds height="55.0" width="105.0" x="503.0" y="310.0"></omgdc:Bounds>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="exclusivegateway2" id="BPMNShape_exclusivegateway2">
        <omgdc:Bounds height="40.0" width="40.0" x="535.0" y="410.0"></omgdc:Bounds>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="endevent1" id="BPMNShape_endevent1">
        <omgdc:Bounds height="35.0" width="35.0" x="890.0" y="413.0"></omgdc:Bounds>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="exclusivegateway3" id="BPMNShape_exclusivegateway3">
        <omgdc:Bounds height="40.0" width="40.0" x="770.0" y="228.0"></omgdc:Bounds>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="reportback" id="BPMNShape_reportback">
        <omgdc:Bounds height="55.0" width="105.0" x="855.0" y="221.0"></omgdc:Bounds>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="startevent1" id="BPMNShape_startevent1">
        <omgdc:Bounds height="35.0" width="35.0" x="140.0" y="230.0"></omgdc:Bounds>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNEdge bpmnElement="flow3" id="BPMNEdge_flow3">
        <omgdi:waypoint x="575.0" y="247.0"></omgdi:waypoint>
        <omgdi:waypoint x="620.0" y="247.0"></omgdi:waypoint>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="48.0" width="24.0" x="575.0" y="247.0"></omgdc:Bounds>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow4" id="BPMNEdge_flow4">
        <omgdi:waypoint x="555.0" y="267.0"></omgdi:waypoint>
        <omgdi:waypoint x="555.0" y="310.0"></omgdi:waypoint>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="48.0" width="24.0" x="555.0" y="267.0"></omgdc:Bounds>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow6" id="BPMNEdge_flow6">
        <omgdi:waypoint x="355.0" y="247.0"></omgdi:waypoint>
        <omgdi:waypoint x="535.0" y="247.0"></omgdi:waypoint>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow7" id="BPMNEdge_flow7">
        <omgdi:waypoint x="555.0" y="365.0"></omgdi:waypoint>
        <omgdi:waypoint x="555.0" y="410.0"></omgdi:waypoint>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow8" id="BPMNEdge_flow8">
        <omgdi:waypoint x="535.0" y="430.0"></omgdi:waypoint>
        <omgdi:waypoint x="302.0" y="429.0"></omgdi:waypoint>
        <omgdi:waypoint x="302.0" y="275.0"></omgdi:waypoint>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="48.0" width="48.0" x="361.0" y="438.0"></omgdc:Bounds>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow9" id="BPMNEdge_flow9">
        <omgdi:waypoint x="575.0" y="430.0"></omgdi:waypoint>
        <omgdi:waypoint x="890.0" y="430.0"></omgdi:waypoint>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="48.0" width="48.0" x="659.0" y="437.0"></omgdc:Bounds>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow10" id="BPMNEdge_flow10">
        <omgdi:waypoint x="725.0" y="247.0"></omgdi:waypoint>
        <omgdi:waypoint x="770.0" y="248.0"></omgdi:waypoint>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow11" id="BPMNEdge_flow11">
        <omgdi:waypoint x="790.0" y="268.0"></omgdi:waypoint>
        <omgdi:waypoint x="789.0" y="337.0"></omgdi:waypoint>
        <omgdi:waypoint x="608.0" y="337.0"></omgdi:waypoint>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="48.0" width="24.0" x="672.0" y="319.0"></omgdc:Bounds>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow12" id="BPMNEdge_flow12">
        <omgdi:waypoint x="810.0" y="248.0"></omgdi:waypoint>
        <omgdi:waypoint x="855.0" y="248.0"></omgdi:waypoint>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="48.0" width="24.0" x="810.0" y="248.0"></omgdc:Bounds>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow13" id="BPMNEdge_flow13">
        <omgdi:waypoint x="907.0" y="276.0"></omgdi:waypoint>
        <omgdi:waypoint x="907.0" y="413.0"></omgdi:waypoint>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="flow14" id="BPMNEdge_flow14">
        <omgdi:waypoint x="175.0" y="247.0"></omgdi:waypoint>
        <omgdi:waypoint x="250.0" y="247.0"></omgdi:waypoint>
      </bpmndi:BPMNEdge>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</definitions>
```
这是一个简单的请假申请流程
放入指定的文件夹中之后，我们重新启动启动类
第二步，查看我们的数据表
`ACT_RE_PROCDEF`中可以查看到我们的流程
![在这里插入图片描述](https://img-blog.csdnimg.cn/7cb6fdef0d3446e9b5a9a276ca7ef0b8.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
至此之后我们的流程就创建完成了，现在来我们的api操作
第三步，由于我们要跟我们的业务连接到一起，所以我们创建一个请假表

```java
CREATE TABLE `leave` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `process_instance_id` varchar(45) DEFAULT NULL COMMENT '流程表id',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户id',
  `start_time` varchar(45) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(45) DEFAULT NULL COMMENT '结束时间',
  `leave_type` varchar(45) DEFAULT NULL COMMENT '请假类型',
  `reason` varchar(400) DEFAULT NULL COMMENT '请假原因',
  `apply_time` datetime DEFAULT NULL COMMENT '通过时间',
  `reality_start_time` varchar(45) DEFAULT NULL COMMENT '请假开始时间',
  `reality_end_time` varchar(45) DEFAULT NULL COMMENT '请假结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='请假表';
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/f329522cd59146209871728ca40a6dfc.png)
第四步，集成底层框架

对了，我们没有数据库框架，这里我们使用[mybatisPlusPro](https://blog.csdn.net/csdnerM/article/details/121565202)来作为底层框架
第五步，创建controller等类

```java
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
```

```java
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfugui.activiti.dao.LeaveDO;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/2
 * @since JDK 1.8.0
 */
public interface LeaveService extends IService<LeaveDO> {
}
```

```java

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfugui.activiti.dao.LeaveDO;
import com.wangfugui.activiti.dao.mapper.LeaveMapper;
import com.wangfugui.activiti.service.LeaveService;
import org.springframework.stereotype.Service;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/12/2
 * @since JDK 1.8.0
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, LeaveDO> implements LeaveService {

}
```java
hellow啊，靓仔，有没有看过前面的内容啊？
请移步到前面的内容去看（一）（二）的内容
接下来我们开始和我们的业务结合到一起。
```

还是先回顾一下上期内容，上期有个遗留的问题，就是mabatis jar包会冲突，所以我们

```java
		  <dependency>
            <groupId>org.activiti</groupId>
            <artifactId>activiti-spring-boot-starter-basic</artifactId>
            <version>5.22.0</version>
            <exclusions>
                <exclusion>
                    <groupId>org.mybatis</groupId>
                    <artifactId>mybatis</artifactId>
                </exclusion>
            </exclusions>

        </dependency>
```
## 第一步，好的，为了接下来方便接口的测试，我们使用swagger来快速搭建

```java
 <!--swagger-->
        <dependency><!--添加Swagger依赖 -->
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.7.0</version>
        </dependency>
        <dependency><!--添加Swagger-UI依赖 -->
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.7.0</version>
        </dependency>
        <!--swagger-->

```
## 第二步，编写controller类

```java
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

```
这次我们先来个简单的创建流程，获取待办流程，审批流程
所以我们就看到了这三个接口
## 第三步，我们进入到实现类中查看

```java
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfugui.activiti.dao.LeaveVo;
import com.wangfugui.activiti.dao.Leaves;
import com.wangfugui.activiti.dao.dto.HandleDto;
import com.wangfugui.activiti.dao.dto.UpcomingDto;
import com.wangfugui.activiti.dao.mapper.LeaveMapper;
import com.wangfugui.activiti.service.LeaveService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

```
这三个的解释已经写在代码里面啦，大家可以去看看注释
## 第四步，我们来看一下我们的测试
发起一个流程
![在这里插入图片描述](https://img-blog.csdnimg.cn/1e3655cdeaf94b80a9f7241d31dcb6a1.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
![在这里插入图片描述](https://img-blog.csdnimg.cn/641eaeb1605549cc8de394e097b0a702.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
查询我们的待办
![在这里插入图片描述](https://img-blog.csdnimg.cn/5fa1bc8d147141a29117fa08ba615eb3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
![在这里插入图片描述](https://img-blog.csdnimg.cn/1bd399ba0b144171bacf14cdd51b9ccf.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
处理我们的待办
![在这里插入图片描述](https://img-blog.csdnimg.cn/e4c9b0bcfb8e4e4abb9416987f6d3893.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
![在这里插入图片描述](https://img-blog.csdnimg.cn/4e0085782dfb425c81673eced6c30da4.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
好了，今天的简单的基本方法就到这里了
下一篇介绍查看历史流程等的集成