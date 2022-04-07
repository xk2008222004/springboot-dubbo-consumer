package com.example.dubbo.springbootdubboconsumer.handler;

import com.example.springbootdubbo.po.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@Slf4j
public class FormatterReturnValueHandler implements HandlerMethodReturnValueHandler {

    private RequestResponseBodyMethodProcessor processor;

    public FormatterReturnValueHandler(RequestResponseBodyMethodProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        log.info("======addReturnValueHandlers=======supportsReturnType===============");
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) || returnType.hasMethodAnnotation(ResponseBody.class);

    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        log.info("====HandlerMethodReturnValueHandler=============handleReturnValue===========");
        if(!(returnValue instanceof ResultObject)){//手动处理就处理不了了
            //不是ResultObject类型 也可能失败
            processor.handleReturnValue(ResultObject.success(returnValue),returnType,mavContainer,webRequest);
        }else{
            processor.handleReturnValue(returnValue,returnType,mavContainer,webRequest);
        }
    }
}
