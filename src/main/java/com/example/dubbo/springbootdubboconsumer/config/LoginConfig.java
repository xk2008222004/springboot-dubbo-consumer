package com.example.dubbo.springbootdubboconsumer.config;

import com.example.dubbo.springbootdubboconsumer.interceptor.AdminInterceptor;
import com.example.springbootdubbo.po.ResultObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.*;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class LoginConfig implements WebMvcConfigurer {


    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistry = registry.addInterceptor(new AdminInterceptor());
        interceptorRegistry.addPathPatterns("/**");
        interceptorRegistry.excludePathPatterns("/getTeacher");
        log.info("=======================the addInterceptors方法被调用创建拦截器");
    }


    //删除不需要的xml的转换器
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
            Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
            while (iterator.hasNext()){
                HttpMessageConverter converter = iterator.next();
                List<MediaType> list = converter.getSupportedMediaTypes();
                int a = list.size();
                int b = list.stream().filter(mediaType -> mediaType.getSubtype().contains("xml")).collect(Collectors.toList()).size();
                if(a==b){
                    iterator.remove();
                }
            }

    }

    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

        HandlerExceptionResolver handlerExceptionResolver = new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("500");
                modelAndView.addObject("message",ex.getMessage());
                return modelAndView;
            }
        };
        resolvers.add(handlerExceptionResolver);
    }

    /*public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }*/

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("upload/**").addResourceLocations("classpath:/upload/");
    }

    /*public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new HandlerMethodReturnValueHandler() {

            private RequestResponseBodyMethodProcessor processor;



            @Override
            public boolean supportsReturnType(MethodParameter returnType) {
                log.info("======addReturnValueHandlers=======supportsReturnType===============");
                return returnType.hasParameterAnnotation(ResponseBody.class);
            }

            @Override
            public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
                log.info("====HandlerMethodReturnValueHandler=============handleReturnValue===========");
                processor.handleReturnValue(ResultObject.success(returnValue),returnType,mavContainer,webRequest);
            }
        });
    }*/


    /*@Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new MyFilter());
        frBean.addUrlPatterns("/*");
        //frBean.addInitParameter("exclusions","/getTeacher");
        logger.info("==============register filter");
        return frBean;
    }*/

    /*@Bean
    public ServletRegistrationBean servletRegister(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.addUrlMappings("/servlet2");
        registrationBean.setServlet(new MyServlet2());
        return registrationBean;
    }*/




}
