package com.example.dubbo.springbootdubboconsumer.config;

import com.example.dubbo.springbootdubboconsumer.handler.FormatterReturnValueHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class InitializingAdvice implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;


    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> list = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>(list);
        decorateHandlerMethodReturnValueHandler(returnValueHandlers);
        adapter.setReturnValueHandlers(returnValueHandlers);
    }

    private void decorateHandlerMethodReturnValueHandler(List<HandlerMethodReturnValueHandler> list){
        for(int i=0;i<list.size();i++){
            HandlerMethodReturnValueHandler temp = list.get(i);
            if(temp instanceof RequestResponseBodyMethodProcessor){
                FormatterReturnValueHandler handler = new FormatterReturnValueHandler((RequestResponseBodyMethodProcessor)temp);
                list.set(i,handler);
                break;
            }
        }
    }

}
