package com.imooc.activitiweb.pojo;

public class Act_ru_task {

    private String NAME_;
    private String TASK_DEF_KEY_;

    public String getNAME_() {
        return NAME_;
    }

    public void setNAME_(String NAME_) {
        this.NAME_ = NAME_;
    }


    public String getTASK_DEF_KEY_() {
        return TASK_DEF_KEY_;
    }

    public void setTASK_DEF_KEY_(String TASK_DEF_KEY_) {
        this.TASK_DEF_KEY_ = TASK_DEF_KEY_;
    }

    @Override
    public String toString() {
        return "act_ru_task{" +
                "NAME_='" + NAME_ + '\'' +
                ", TASK_DEF_KEY_='" + TASK_DEF_KEY_ + '\'' +
                '}';
    }

}
