package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;

public class PiListener implements ExecutionListener {
    @Autowired

    private Expression sendType;
    @Override
    public void notify(DelegateExecution execution) {
        System.out.println(execution.getEventName());
        System.out.println(execution.getProcessDefinitionId());
        if("start".equals(execution.getEventName())){
            //记录节点开始时间
        }else if("end".equals(execution.getEventName())){
            //记录节点结束时间
        }
        System.out.println("sendType:"+sendType.getValue(execution).toString());


    }
}
