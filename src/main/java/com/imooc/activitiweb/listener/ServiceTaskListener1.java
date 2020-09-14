package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTaskListener1 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {

        System.out.println(execution.getEventName());
        System.out.println(execution.getProcessDefinitionId());
        System.out.println(execution.getProcessInstanceId());

        execution.setVariable("aa","bb");
    }
}
