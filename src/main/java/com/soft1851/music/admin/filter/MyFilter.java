package com.soft1851.music.admin.filter;

/**
 * @author wl
 * @ClassNamesdadsa
 * @Description TODO
 * @Date 2020/4/21
 * @Version 1.0
 */


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * @ClassName ChannelFilter
 * @Description 过滤器Filter，用来把request传递下去
 * @Author mq_xu
 * @Date 2020/4/21
 * @Version 1.0
 */
@Order(1)
@WebFilter(filterName = "myFilter", urlPatterns = "/*")
@Slf4j
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("执行过滤器");
        if (servletRequest instanceof HttpServletRequest) {
            log.info("有http请求进入过滤器");
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String header = httpServletRequest.getHeader("Content-Type");
            if (header != null) {
                log.info(header);
                if (header.startsWith("multipart/form-data")) {
                    log.info("有文件上传请求");
                    Part file = httpServletRequest.getPart("file");
                    log.info("文件名：" + file.getSubmittedFileName());
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}