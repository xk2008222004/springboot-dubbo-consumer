package com.example.dubbo.springbootdubboconsumer.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.dubbo.springbootdubboconsumer.config.RabbitMqConfig;
import com.example.springbootdubbo.annotation.BuziExceptionHandler;
import com.example.springbootdubbo.dict.ParamDict;
import com.example.springbootdubbo.po.BuziException;
import com.example.springbootdubbo.po.ResultObject;
import com.example.springbootdubbo.po.StudentEx;
import com.example.springbootdubbo.po.StudentStatic;
import com.example.springbootdubbo.service.RabbitMqService;
import com.example.springbootdubbo.service.RedisService;
import com.example.springbootdubbo.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.SpringVersion;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
@BuziExceptionHandler
public class TeacherController {

    //TODO
    //写异常处理器 返回json格式  还有404处理

    //TODO
    //
    @Reference(interfaceClass = RedisService.class,version = "1.0.0")
    private RedisService redisService;

    @Reference(interfaceClass = TeacherService.class,version = "*",check = true)
    private TeacherService teacherService;

    @Reference(interfaceClass = RabbitMqService.class,version = "1.0.0",group = "fixTemplate")
    private RabbitMqService rabbitMqService;


    @Value("${app.description}")
    private String appDesc;



    @RequestMapping(value = "/getTeacher",params = "new=123")//status=400
    public @ResponseBody
    String getStudent(HttpServletRequest request) {
        String str = teacherService.getTeacher("焦裕禄");
        request.getSession().setAttribute("user",str);
        redisService.set("user",str);
        return str;
    }

    @RequestMapping(value="/getRedisValue")
    @ResponseBody
    public String getRedisValue(String key){
        return (String)redisService.get(key);
    }

    @RequestMapping(value="/sendMessage")
    @ResponseBody
    public String sendMessage(@RequestParam(required = false,defaultValue = "1111111") String value, Model model){
        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("messageId",messageId);
        model.addAttribute("createTime",createTime);
        model.addAttribute("messageData",value);
        rabbitMqService.sendExchangeByRouting(ParamDict.DIRECTEXCHANGE,ParamDict.DIRECTROUTING,model.asMap());
        return "ok";
    }

    @RequestMapping(value="/sendTopMessage")
    @ResponseBody
    public String sendTopMessage(@RequestParam(required = false,defaultValue = "2222222") String value,Model model){
        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("messageId",messageId);
        model.addAttribute("createTime",createTime);
        model.addAttribute("messageData",value);
        rabbitMqService.sendExchangeByRouting(ParamDict.TOPICEXCHANGE,ParamDict.MAN,model.asMap());
        return "ok";
    }

    @RequestMapping(value="/sendTopMessage2")
    @ResponseBody
    public String sendTopMessage2(@RequestParam(required = false,defaultValue = "3333333")  String value,Model model){
        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("messageId",messageId);
        model.addAttribute("createTime",createTime);
        model.addAttribute("messageData",value);

        //ParamDict.WOMAN 为消息携带的路由键
        rabbitMqService.sendExchangeByRouting(ParamDict.TOPICEXCHANGE,ParamDict.WOMAN,model.asMap());
        return "ok";
    }

    @RequestMapping(value="/sendTopMessageFanout")
    @ResponseBody
    public String sendTopMessageFanout(@RequestParam(required = false,defaultValue = "3333333")  String value,Model model){
        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("messageId",messageId);
        model.addAttribute("createTime",createTime);
        model.addAttribute("messageData",value);

        //ParamDict.WOMAN 为消息携带的路由键
        rabbitMqService.sendExchangeByRouting(ParamDict.FADOUTEXCHANGE,null,model.asMap());
        return "ok";
    }

    @RequestMapping(value="/sendMessageCallbackNoExchange")
    @ResponseBody
    public String sendMessageCallbackNoExchange(@RequestParam(required = false,defaultValue = "4444444")  String value,Model model){
        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("messageId",messageId);
        model.addAttribute("createTime",createTime);
        model.addAttribute("messageData",value);

        //ParamDict.WOMAN 为消息携带的路由键
        rabbitMqService.sendExchangeByRouting("no-config-exchange","no-route",model.asMap());
        return "ok";
    }

    @RequestMapping(value="/sendMessageCallbackHasExchange")
    @ResponseBody
    public String sendMessageCallbackHasExchange(@RequestParam(required = false,defaultValue = "5555555")  String value,Model model){
        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("messageId",messageId);
        model.addAttribute("createTime",createTime);
        model.addAttribute("messageData",value);

        //ParamDict.WOMAN 为消息携带的路由键
        rabbitMqService.sendExchangeByRouting(ParamDict.DIRECTEXCHANGE,"no-route",model.asMap());
        return "ok";
    }

    @RequestMapping(value="/sendMessageCallbackHasExchangeWithRoute")
    @ResponseBody
    public String sendMessageCallbackHasExchangeWithRoute(@RequestParam(required = false,defaultValue = "666666")  String value,Model model){
        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("messageId",messageId);
        model.addAttribute("createTime",createTime);
        model.addAttribute("messageData",value);
        //ParamDict.WOMAN 为消息携带的路由键
        rabbitMqService.sendExchangeByRouting(ParamDict.DIRECTEXCHANGE,ParamDict.DIRECTROUTING,model.asMap());
        return "ok";
    }



    @RequestMapping(value = "/logOutTeacher")
    public @ResponseBody
    String logOut(HttpServletRequest request) {
        String str = teacherService.getTeacher("焦裕禄");
        request.getSession().invalidate();
        return str;
    }

    @RequestMapping(value = "/removeAttr")
    public @ResponseBody
    String removeAttr(HttpServletRequest request) {
        String str = teacherService.getTeacher("焦裕禄");
        request.getSession().removeAttribute("user");
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
    public String getStatic2(Model model){
        try{
            int x = 1/0;
            model.addAttribute("attr","this is a attr attribute");
        }catch (Exception ex){
            throw new BuziException("00","异常了");
        }
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/getDataBind2")
    public StudentEx testJsonXmlDataBind2(){
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
