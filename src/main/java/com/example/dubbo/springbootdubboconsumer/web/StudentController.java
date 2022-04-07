package com.example.dubbo.springbootdubboconsumer.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.dubbo.springbootdubboconsumer.vo.SysLogExtNew;
import com.example.springbootdubbo.annotation.SysLogHandler;
import com.example.springbootdubbo.po.StudentEx;
import com.example.springbootdubbo.po.SysLog;
import com.example.springbootdubbo.service.StudentExService;
import com.example.springbootdubbo.service.StudentService;
import com.example.springbootdubbo.service.SysLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@SysLogHandler
public class StudentController {

    @Reference(interfaceClass = StudentService.class,version = "1.0.0",check = false)
    private StudentService studentService;

    @Reference(interfaceClass = StudentExService.class,version = "1.0.0",check = false)
    private StudentExService studentExService;

    @Reference(interfaceClass = SysLogService.class,version = "1.0.0")
    private SysLogService sysLogService;

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


    @RequestMapping("/getLogList")
    public List<SysLog>  getLogList(SysLogExtNew sysLog){
        //TODO
        //使用消息转换器
        SysLog log = new SysLog();
        BeanUtils.copyProperties(sysLog,log);
        if(sysLog.getStartTime()!=null){
            log.setStartTime(new Date(sysLog.getStartTime()));
        }
        if(sysLog.getEndTime()!=null){
            log.setEndTime(new Date(sysLog.getEndTime()));
        }
        return sysLogService.queryLog(log);
    }
}
