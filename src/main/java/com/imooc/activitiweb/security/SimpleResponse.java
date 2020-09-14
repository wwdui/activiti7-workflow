package com.imooc.activitiweb.security;

public class SimpleResponse {


    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public SimpleResponse(Object content) {
        this.content = content;
    }

    private Object content;


}
