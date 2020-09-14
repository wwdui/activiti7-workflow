package com.imooc.activitiweb.controller;

import com.imooc.activitiweb.SecurityUtil;
import com.imooc.activitiweb.pojo.UserInfoBean;
import com.imooc.activitiweb.util.AjaxResponse;
import com.imooc.activitiweb.util.GlobalConfig;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/processInstance")
public class ProcessInstanceController {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RepositoryService repositoryService;




    @GetMapping(value = "/getInstances")
    public AjaxResponse getInstances(@AuthenticationPrincipal UserInfoBean userInfoBean) {
        Page<ProcessInstance> processInstances = null;
        try {
            //测试用写死的用户POSTMAN测试用；生产场景已经登录，在processDefinitions中可以获取到当前登录用户的信息
            if (GlobalConfig.Test) {
                securityUtil.logInAs("wukong");
            }
            //else {
            //  securityUtil.logInAs(userInfoBean.getUsername());//这句不需要
            // }
            processInstances=processRuntime.processInstances(Pageable.of(0,  50));
            //System.out.println("流程实例数量： " + processInstances.getTotalItems());
            List<ProcessInstance> list = processInstances.getContent();
            //list.sort((y,x)->x.getProcessDefinitionVersion()-y.getProcessDefinitionVersion());
            list.sort((y,x)->x.getStartDate().toString().compareTo(y.getStartDate().toString()));

            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            for(ProcessInstance pi:list){
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", pi.getId());
                hashMap.put("name", pi.getName());
                hashMap.put("status", pi.getStatus());
                hashMap.put("processDefinitionId", pi.getProcessDefinitionId());
                hashMap.put("processDefinitionKey", pi.getProcessDefinitionKey());
                hashMap.put("startDate", pi.getStartDate());
                hashMap.put("processDefinitionVersion", pi.getProcessDefinitionVersion());
                //因为processRuntime.processDefinition("流程部署ID")查询的结果没有部署流程与部署ID，所以用repositoryService查询
                ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                        .processDefinitionId(pi.getProcessDefinitionId())
                        .singleResult();
                hashMap.put("resourceName", pd.getResourceName());
                hashMap.put("deploymentId", pd.getDeploymentId());
                listMap.add(hashMap);
            }

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(),listMap);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取流程实例失败", e.toString());
        }


    }


    //启动
    @GetMapping(value = "/startProcess")
    public AjaxResponse startProcess(@RequestParam("processDefinitionKey") String processDefinitionKey,
                                     @RequestParam("instanceName") String instanceName,
                                     @RequestParam("instanceVariable") String instanceVariable) {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("bajie");
            }else{
                securityUtil.logInAs(SecurityContextHolder.getContext().getAuthentication().getName());
            }
            ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                    .start()
                    .withProcessDefinitionKey(processDefinitionKey)
                    .withName(instanceName)
                    //.withVariable("content", instanceVariable)
                    //.withVariable("参数2", "参数2的值")
                    .withBusinessKey("自定义BusinessKey")
                    .build());
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), processInstance.getName()+"；"+processInstance.getId());
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "创建流程实例失败", e.toString());
        }
    }

    //删除
    @GetMapping(value = "/deleteInstance")
    public AjaxResponse deleteInstance(@RequestParam("instanceID") String instanceID) {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("wukong");
            }

            ProcessInstance processInstance = processRuntime.delete(ProcessPayloadBuilder
                    .delete()
                    .withProcessInstanceId(instanceID)
                    .build()
            );
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), processInstance.getName());
        }
     catch(Exception e)
        {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "删除流程实例失败", e.toString());
        }

    }

    //挂起冷冻
    @GetMapping(value = "/suspendInstance")
    public AjaxResponse suspendInstance(@RequestParam("instanceID") String instanceID) {

        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("wukong");
            }

            ProcessInstance processInstance = processRuntime.suspend(ProcessPayloadBuilder
                    .suspend()
                    .withProcessInstanceId(instanceID)
                    .build()
            );
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), processInstance.getName());
        }
        catch(Exception e)
        {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "挂起流程实例失败", e.toString());
        }
    }

    //激活
    @GetMapping(value = "/resumeInstance")
    public AjaxResponse resumeInstance(@RequestParam("instanceID") String instanceID) {

        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("wukong");
            }

            ProcessInstance processInstance = processRuntime.resume(ProcessPayloadBuilder
                    .resume()
                    .withProcessInstanceId(instanceID)
                    .build()
            );
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), processInstance.getName());
        }
        catch(Exception e)
        {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "激活流程实例失败", e.toString());
        }
    }


    //获取参数
    @GetMapping(value = "/variables")
    public AjaxResponse variables(@RequestParam("instanceID") String instanceID) {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("wukong");
            }
            List<VariableInstance> variableInstance = processRuntime.variables(ProcessPayloadBuilder
                    .variables()
                    .withProcessInstanceId(instanceID)
                    .build());

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), variableInstance);
        }
        catch(Exception e)
        {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取流程参数失败", e.toString());
        }
    }





    
}
