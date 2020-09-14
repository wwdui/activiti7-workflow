package com.imooc.activitiweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActivitiwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiwebApplication.class, args);
    }

}


/*

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.imooc.activitiweb"})
public class ActivitiwebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ActivitiwebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ActivitiwebApplication.class, args);
    }

}*/
