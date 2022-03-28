package com.example.dubbo.springbootdubboconsumer.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.springbootdubbo.po.StudentEx;
import com.example.springbootdubbo.po.StudentStatic;
import com.example.springbootdubbo.service.TeacherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
public class TeacherController {

    @Reference(interfaceClass = TeacherService.class,version = "*",check = true)
    private TeacherService teacherService;



    @Value("${app.description}")
    private String appDesc;



    @RequestMapping(value = "/getTeacher")
    public @ResponseBody
    String getStudent(HttpServletRequest request) {
        String str = teacherService.getTeacher("焦裕禄");
        request.getSession().setAttribute("user",str);
        return str;
    }

    @RequestMapping(value="/getConfig")
    public @ResponseBody String getConfig(){
        return appDesc;
    }

    @ResponseBody
    @RequestMapping(value="/getDataBind")
    public StudentEx testJsonXmlDataBind(){
        StudentEx studentEx = new StudentEx();
        studentEx.setName("lisi");
        studentEx.setId(12);
        studentEx.setAge(1);
        return studentEx;
    }

    @ResponseBody
    @RequestMapping(value="/getSuffix")
    public StudentStatic testXml(){
        StudentStatic studentStatic = new StudentStatic();
        studentStatic.setCountStu(1);
        studentStatic.setStudentName("zhangsan");
        return studentStatic;
    }

    @ResponseBody
    @RequestMapping(value="/testVersion")
    public String testVersion(){
        return SpringVersion.getVersion();
    }


}
