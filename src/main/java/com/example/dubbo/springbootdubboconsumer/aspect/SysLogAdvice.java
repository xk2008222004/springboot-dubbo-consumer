package com.example.dubbo.springbootdubboconsumer.aspect;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.springbootdubbo.annotation.SysLogHandler;
import com.example.springbootdubbo.dict.ParamDict;
import com.example.springbootdubbo.po.BuziException;
import com.example.springbootdubbo.po.SysLog;
import com.example.springbootdubbo.service.RabbitMqService;
import com.example.springbootdubbo.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.security.SignedObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class SysLogAdvice {

    @Reference(interfaceClass = RabbitMqService.class,version = "1.0.0",group = "delayTemplate")
    private RabbitMqService rabbitMqService;

    @Pointcut("execution (* com.example.dubbo.springbootdubboconsumer.web.*.*(..))")
    public void pp(){}

    @Around("pp()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        Signature signature = point.getSignature();
        //方法
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        //类
        Method realMethod = point.getTarget().getClass().getDeclaredMethod(signature.getName(),method.getParameterTypes());
        Class realTarget = point.getTarget().getClass();
        Object result = null;
        SysLog sysLog = new SysLog();
        boolean flag = true;
        Exception exception = null;
        if(realMethod.isAnnotationPresent(SysLogHandler.class)||realTarget.isAnnotationPresent(SysLogHandler.class)){//
            try{
                sysLog.setCreateTime(new Date());
                sysLog.setMethod(realMethod.getName());
                sysLog.setModel(realTarget.getSimpleName());
                sysLog.setParam(SysLogAdvice.getParam(point));
                sysLog.setResult(0);
                result = point.proceed();
            }catch (BuziException e){
                log.info("=========业务异常,{}",e);
                sysLog.setResult(-1);
                sysLog.setFullLog("业务异常");
                flag=false;
                exception = e;
            }catch (Exception ex){
                log.info("=======系统异常=========={}",ex);
                sysLog.setResult(-1);
                sysLog.setFullLog("系统异常");
                flag=false;
                exception = ex;
            }
            String messageId = UUID.randomUUID().toString();
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String,Object> map = new ConcurrentHashMap<>();
            map.put("messageId",messageId);
            map.put("createTime",createTime);
            map.put("messageData",sysLog);
            rabbitMqService.sendExchangeByRouting(ParamDict.LOGDELAYDIRECTEXCHANGE,ParamDict.SYSLOGDELAYDIRECTROUTING,map);
        }else{
            result = point.proceed();
        }
        if(!flag){
            throw exception;
        }
        return result;

    }

    private static String getParam(ProceedingJoinPoint joinPoint) throws NoSuchMethodException{
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        String[] paraNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        if (paraNames != null && paraNames.length > 0 && args != null && args.length > 0) {
            for (int i = 0; i <paraNames.length; i++) {
                sb.append(paraNames[i] + ":" + args[i] + ",");
            }
        }
        if(sb.length()>3){
            sb.delete(sb.length() - 1, sb.length() + 1);
        }
        String param = sb.toString();
        return param;
    }
}
