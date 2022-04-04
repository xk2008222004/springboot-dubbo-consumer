package com.example.dubbo.springbootdubboconsumer.aspect;

import com.example.springbootdubbo.annotation.BuziExceptionHandler;
import com.example.springbootdubbo.po.BuziException;
import com.example.springbootdubbo.po.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;

@Slf4j
//@Component
//@Aspect
public class BuziExceptionAdvice {

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
        if((realMethod.isAnnotationPresent(BuziExceptionHandler.class)||realTarget.isAnnotationPresent(BuziExceptionHandler.class))&&realMethod.isAnnotationPresent(ResponseBody.class)
                &&realMethod.getReturnType()==ResultObject.class){//
            try{
                result = point.proceed();
            }catch (BuziException e){
                log.info("=========业务异常,{}",e);
                result = ResultObject.fail("业务异常");
            }catch (Exception ex){
                log.info("=======系统异常=========={}",ex);
                result = ResultObject.fail("系统异常");
            }
        }else{
            result = point.proceed();
        }
        return result;

    }



}
