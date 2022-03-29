package com.example.dubbo.springbootdubboconsumer.filter;

import com.example.dubbo.springbootdubboconsumer.bo.CorsBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@WebFilter(urlPatterns = "/*")
@Order(2)
public class CORSFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(CORSFilter.class);

    @Autowired
    @Lazy
    private CorsBo corsBo;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("=================CORSFilter init2");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("=============CORSFilterFaked doFilter");
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        /*response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Access-Control-Allow-Origin","http://localhost:8085");
        response.addHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT");
        response.addHeader("Access-Control-Allow-Headers","Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
        */
        ServletServerHttpResponse resp2 = new ServletServerHttpResponse(response);
        ServletServerHttpRequest requ2 = new ServletServerHttpRequest(request);
        HttpHeaders httpHeaders = resp2.getHeaders();
        String orgin = requ2.getHeaders().getOrigin();
        //解决跨域的问题
        String configOrigin = corsBo.getOrigins();
        if(orgin!=null && (orgin.equals(configOrigin)||"*".equals(configOrigin))){
            httpHeaders.setAccessControlAllowOrigin(orgin);
            httpHeaders.setAccessControlAllowCredentials(corsBo.isCredential());
            httpHeaders.setAccessControlAllowMethods(corsBo.getMethods().stream().
                    map(method->HttpMethod.resolve(method)).collect(Collectors.toList()));
            httpHeaders.setAccessControlMaxAge(corsBo.getMaxAge());
            resp2.flush();
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("=================CORSFilter destroy");
    }
}
