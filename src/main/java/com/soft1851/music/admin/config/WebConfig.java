package com.soft1851.music.admin.config;

import com.soft1851.music.admin.intercepter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author wl
 * @ClassNameasd
 * @Description TODO
 * @Date 2020/4/21
 * @Version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;
    /**
     * 添加拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径可自行配置多个 可用 ，分隔开
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/sysAdmin/login").excludePathPatterns("/static/**");
    }
}

