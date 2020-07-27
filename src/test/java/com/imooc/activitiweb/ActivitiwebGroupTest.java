package com.imooc.activitiweb;


import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@SpringBootTest
public class ActivitiwebGroupTest {
    private Logger logger = LoggerFactory.getLogger(ActivitiwebGroupTest.class);

    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private SecurityUtil securityUtil;

    @Test
    public void initProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();//创建处理引擎实例
        RepositoryService repositoryService = processEngine.getRepositoryService();//创建仓库服务实例
        Deployment deployment = repositoryService.createDeployment()//初始化流程
                .addClasspathResource("processesbpmn/DocPushGroupV2.bpmn")
                .name("发文流程组")
                .deploy();
        System.out.println(deployment.getName());
    }

    @Test
    public void getProcess() {
        System.out.println("单元测试开始1");
        securityUtil.logInAs("admin");
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        logger.info("> Available Process definitions: " + processDefinitionPage.getTotalItems());
        for (ProcessDefinition pd : processDefinitionPage.getContent()) {
            logger.info("\t > Process definition: " + pd);
        }
        logger.info("单元测试结束1");

    }


    @Test
    public void processTaskStart() {

        securityUtil.logInAs("admin");

        String content = pickRandomString();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

        logger.info("> Processing content: " + content + " at " + formatter.format(new Date()));

        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("myProcess_GROUP_activitiTeamV2")
                .withName("Processing Content: " + content)
                .withVariable("content", content)
                .build());
        logger.info(">>> Created Process Instance: " + processInstance);
        logger.info("单元测试结束2");

    }

    private String pickRandomString() {
        String[] texts = {"hello from london", "Hi there from activiti!", "all good news over here.", "I've tweeted about activiti today.",
                "other boring projects.", "activiti cloud - Cloud Native Java BPM"};
        return texts[new Random().nextInt(texts.length)];
    }

    @Test
    public void TaskGet() {
        securityUtil.logInAs("wukong");

        // Let's get all my tasks (as 'other' user)
        logger.info("> 登录人所有任务");
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));

        // No tasks are returned
        logger.info(">  登录人的任务数量: " + tasks.getTotalItems());


    }

    @Test
    public void TaskComplete_bajie() {
        securityUtil.logInAs("bajie");

        // Let's get all my tasks (as 'other' user)
        logger.info("> 获取登录人所有任务");
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));

        // No tasks are returned
        logger.info(">  bajie的任务数量: " + tasks.getTotalItems());

        for(Task task:tasks.getContent()){
            logger.info("任务："+task);
            logger.info("任务执行人："+task.getAssignee());//能查到并且执行人是null，是没有拾取得任务
            if(task.getAssignee()==null){
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
            }
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
        }

    }

    @Test
    public void TaskComplete_wukong() {
        securityUtil.logInAs("wukong");

        // Let's get all my tasks (as 'other' user)
        logger.info("> 获取登录人所有任务");
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));

        // No tasks are returned
        logger.info(">  wukong的任务数量: " + tasks.getTotalItems());

        for(Task task:tasks.getContent()){
            logger.info("任务："+task);
            logger.info("任务执行人："+task.getAssignee());//能查到并且执行人是null，是没有拾取得任务
            if(task.getAssignee()==null){
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
            }
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
        }

    }

    //初步构想，需要测试
    @Test
    public void suspendAndresume(){
        Page<ProcessInstance> processInstances=processRuntime.processInstances(Pageable.of(0, 10));

        for(ProcessInstance pi:processInstances.getContent()){
            /*SuspendProcessPayload suspendProcessPayload=new SuspendProcessPayload(pi.getId());//挂起
            processRuntime.suspend(suspendProcessPayload);
            ResumeProcessPayload resumeProcessPayload=new ResumeProcessPayload(pi.getId());//激活
            processRuntime.resume(resumeProcessPayload);*/

            ProcessInstance processInstance = processRuntime.suspend(ProcessPayloadBuilder
                     .suspend()
                     .withProcessInstanceId(pi.getId())
                     .build()
             );

            ProcessInstance processInstance1= processRuntime.resume(ProcessPayloadBuilder
                    .resume()
                    .withProcessInstanceId(pi.getId())
                    .build()
            );

            ProcessInstance processInstance2= processRuntime.delete(ProcessPayloadBuilder
                    .delete()
                    .withProcessInstanceId(pi.getId())
                    .build()
            );

        }



    }
}


