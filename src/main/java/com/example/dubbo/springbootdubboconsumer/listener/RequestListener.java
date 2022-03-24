package com.example.dubbo.springbootdubboconsumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestListener implements ServletRequestListener {

    private static Logger logger = LoggerFactory.getLogger(RequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent event){
        logger.info("request has destroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        logger.info("request has initialized");
    }



}
