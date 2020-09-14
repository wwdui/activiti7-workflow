package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ErrServiceTaskListener implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        throw new BpmnError("Error_21ldg70");
    }
}
