package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MultInstancesTKListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("执行人："+delegateTask.getAssignee());
        //根据任务节点逻辑查询实际需要的执行人是谁
        delegateTask.setAssignee("wukong");
    }
}
