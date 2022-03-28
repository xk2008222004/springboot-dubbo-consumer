package com.example.dubbo.springbootdubboconsumer.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class MyFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("================启动时初始化过滤器");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest)servletRequest;
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse)servletResponse);
        logger.info("===============doFilter  filter");
        //String user = (String)request.getSession().getAttribute("user");//无法获取到
        if(hrequest.getRequestURI().indexOf("/getTeacher") != -1 ||
                hrequest.getRequestURI().indexOf("/asd") != -1 ||
                hrequest.getRequestURI().indexOf("/online") != -1 ||
                hrequest.getRequestURI().indexOf("/login") != -1
        ) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            //先走登录
            wrapper.sendRedirect("/getTeacher");
        }
    }

    @Override
    public void destroy() {
        logger.info("================摧毁过滤器");
    }
}
