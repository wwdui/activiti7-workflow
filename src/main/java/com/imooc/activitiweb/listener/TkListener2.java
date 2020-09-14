package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TkListener2 implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("执行人2："+delegateTask.getVariable("delegateAssignee"));
        //根据执行人username获取组织机构代码，加工后得到领导是wukong
        delegateTask.setAssignee("wukong");
    }
}
