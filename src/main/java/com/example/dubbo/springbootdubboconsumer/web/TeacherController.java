package com.example.dubbo.springbootdubboconsumer.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.springbootdubbo.annotation.BuziExceptionHandler;
import com.example.springbootdubbo.po.BuziException;
import com.example.springbootdubbo.po.ResultObject;
import com.example.springbootdubbo.po.StudentEx;
import com.example.springbootdubbo.po.StudentStatic;
import com.example.springbootdubbo.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.SpringVersion;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@BuziExceptionHandler
public class TeacherController {

    //TODO
    //写异常处理器 返回json格式  还有404处理

    //TODO
    //

    @Reference(interfaceClass = TeacherService.class,version = "*",check = true)
    private TeacherService teacherService;



    @Value("${app.description}")
    private String appDesc;



    @RequestMapping(value = "/getTeacher",params = "new=123")//status=400
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
    @RequestMapping(value="/getDataBind",produces = {MediaType.APPLICATION_JSON_VALUE})
    public StudentEx testJsonXmlDataBind(){
        StudentEx studentEx = new StudentEx();
        studentEx.setName("lisi");
        studentEx.setId(12);
        studentEx.setAge(1);
        return studentEx;
    }

    @ResponseBody
    @RequestMapping(value="/getDataBind3")
    public ResultObject testJsonXmlDataBind3(){
        StudentEx studentEx = new StudentEx();
        studentEx.setName("lisi");
        studentEx.setId(12);
        studentEx.setAge(1);
        return ResultObject.success(studentEx);
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

    @RequestMapping(value="/test/queryParameter")
    @ResponseBody
    public String getIncoming(RequestEntity<String> requestEntity){
        HttpMethod httpMethod = requestEntity.getMethod();
        log.info("httpMethod={}",httpMethod.name());
        log.info("request body={}",requestEntity.getBody());
        log.info("请求头={}",requestEntity.getHeaders());
        return "ok";
    }

    @RequestMapping("/getStatic")
    public String getStatic(Model model){
        try{
            int x = 1/0;
            model.addAttribute("attr","this is a attr attribute");
        }catch (BuziException ex){
            throw ex;
        }
        return "index";
    }

    @RequestMapping("/getStatic2")
    @ResponseBody
    public Object getStatic2(Model model){
        try{
            int x = 1/0;
            model.addAttribute("attr","this is a attr attribute");
        }catch (Exception ex){
            log.error("ex error info={}",ex);
            throw new BuziException("00","业务异常");
        }
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/getDataBind2")
    public Object testJsonXmlDataBind2(){
        int a = 1/0;
        StudentEx studentEx = new StudentEx();
        studentEx.setName("lisi");
        studentEx.setId(12);
        studentEx.setAge(1);
        return studentEx;
    }

    @ResponseBody
    @RequestMapping(value="/getDataBind4")
    public ResultObject testJsonXmlDataBind4(){
        int a = 1/0;
        StudentEx studentEx = new StudentEx();
        studentEx.setName("lisi");
        studentEx.setId(12);
        studentEx.setAge(1);
        return ResultObject.success(studentEx);
    }


}
