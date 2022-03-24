package com.example.dubbo.springbootdubboconsumer.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.springbootdubbo.service.TeacherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TeacherController {

    @Reference(interfaceClass = TeacherService.class,version = "*",check = true)
    private TeacherService teacherService;

    @Value("${app.description}")
    private String appDesc;



    @RequestMapping(value = "/getTeacher")
    public @ResponseBody
    String getStudent() {
        String str = teacherService.getTeacher("焦裕禄");
        return str;
    }

    @RequestMapping(value="/getConfig")
    public @ResponseBody String getConfig(){
        return appDesc;
    }


}
