package com.example.dubbo.springbootdubboconsumer.config;

import com.example.dubbo.springbootdubboconsumer.filter.MyFilter;
import com.example.dubbo.springbootdubboconsumer.interceptor.AdminInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    private static Logger logger = LoggerFactory.getLogger(LoginConfig.class);

    /*public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistry = registry.addInterceptor(new AdminInterceptor());
        interceptorRegistry.addPathPatterns("/**");
        interceptorRegistry.excludePathPatterns("/getTeacher");
    }*/


    @Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new MyFilter());
        frBean.addUrlPatterns("/*");
        //frBean.addInitParameter("exclusions","/getTeacher");
        logger.info("==============register filter");
        return frBean;
    }




}
