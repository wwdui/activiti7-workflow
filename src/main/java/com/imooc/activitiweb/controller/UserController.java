package com.imooc.activitiweb.controller;

import com.imooc.activitiweb.mapper.ActivitiMapper;
import com.imooc.activitiweb.util.AjaxResponse;
import com.imooc.activitiweb.util.GlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ActivitiMapper mapper;

    //获取用户
    @GetMapping(value = "/getUsers")
    public AjaxResponse getUsers() {
        try {
            List<HashMap<String, Object>> userList = mapper.selectUser();


            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), userList);


        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取用户列表失败", e.toString());
        }
    }

}
