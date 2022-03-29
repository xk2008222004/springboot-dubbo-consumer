package com.example.dubbo.springbootdubboconsumer.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Order(3)
public class MyFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("================启动时初始化过滤器3");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("===============MyFilter  filter");
        filterChain.doFilter(servletRequest, servletResponse);
        //String user = (String)request.getSession().getAttribute("user");//无法获取到
        /*if(hrequest.getRequestURI().indexOf("/getTeacher") != -1 ||
                hrequest.getRequestURI().indexOf("/asd") != -1 ||
                hrequest.getRequestURI().indexOf("/online") != -1 ||
                hrequest.getRequestURI().indexOf("/login") != -1
        ) {

        }else {
            //先走登录
            wrapper.sendRedirect("/getTeacher");
        }*/
    }

    @Override
    public void destroy() {
        logger.info("================摧毁过滤器");
    }
}
