package com.example.dubbo.springbootdubboconsumer.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.springbootdubbo.po.StudentEx;
import com.example.springbootdubbo.service.StudentExService;
import com.example.springbootdubbo.service.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class StudentController {

    @Reference(interfaceClass = StudentService.class,version = "1.0.0",check = false)
    private StudentService studentService;

    @Reference(interfaceClass = StudentExService.class,version = "1.0.0",check = false)
    private StudentExService studentExService;

    /*@Value("${test.ab}")
    @Lazy*/
    private String importPro;

    @RequestMapping(value = "/getStudent")
    public String getStudent(@RequestParam(name="id",required = false) Integer id) {
        if(id!=null){
            StudentEx ex = studentExService.getStudentById(Long.valueOf(id));
            return ex.getName();
        }
        String str = studentService.getStudent();
        return str;
    }

    @RequestMapping(value="/getPro")
    public String getPro(){
        return importPro;
    }
}
