package com.example.dubbo.springbootdubboconsumer.aspect;

import com.example.springbootdubbo.po.BuziException;
import com.example.springbootdubbo.po.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

//@ControllerAdvice(basePackages = {"com.example.dubbo.springbootdubboconsumer.web"})
@Slf4j
public class MyControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {BuziException.class,Exception.class})
    public ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
            //HttpStatus status = getStatus(request);
            log.error(ex.toString());
            String message = "";
            if(ex instanceof BuziException){
                message = "业务异常";
            }else if(ex instanceof Exception){
                message = "系统异常";
            }
            return new ResponseEntity<ResultObject>(ResultObject.fail(message),HttpStatus.OK);
    }

    /*@ExceptionHandler()
    public String handleControllerException2(Exception ex, Model model) {
        log.error(ex.toString());
        model.addAttribute("message",ex.toString());
        return "500";
    }

    @ExceptionHandler({RuntimeException.class})
    public ModelAndView fix(Exception ex){
        System.out.println("do This");
        return new ModelAndView("error",new ModelMap("ex",ex.getMessage()));
    }*/


}
