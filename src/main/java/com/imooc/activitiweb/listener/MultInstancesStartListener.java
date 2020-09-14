package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import java.util.ArrayList;

public class MultInstancesStartListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) {
        ArrayList<String> assigneeList = new ArrayList<>();
        assigneeList.add("bajie");
        assigneeList.add("wukong");
        assigneeList.add("salaboy");

        execution.setVariable("assigneeList",assigneeList);

        //execution.setVariable("isPass",0);

    }

}
