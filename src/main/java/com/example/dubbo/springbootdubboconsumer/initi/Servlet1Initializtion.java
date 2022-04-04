package com.example.dubbo.springbootdubboconsumer.initi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
@Slf4j
public class Servlet1Initializtion implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(new HttpSessionListener(){
            public void sessionCreated(HttpSessionEvent se) {
                se.getSession().setMaxInactiveInterval(60);//秒为单位 到期执行destroySession
                log.info("HttpSessionEvent======sessionCreated=======");
            }

            public void sessionDestroyed(HttpSessionEvent se) {
                log.info("HttpSessionEvent====sessionDestroy=========");
            }
        });
        servletContext.addListener(new HttpSessionAttributeListener() {
            @Override
            public void attributeAdded(HttpSessionBindingEvent se) {
                log.info("HttpSessionBindingEvent====attributeAdded======value==="+se.getValue());
            }

            @Override
            public void attributeRemoved(HttpSessionBindingEvent se) {
                log.info("HttpSessionBindingEvent====attributeRemoved=====value=="+se.getValue());
            }
        });
    }
}
