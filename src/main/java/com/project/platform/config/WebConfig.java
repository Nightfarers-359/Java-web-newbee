package com.project.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.platform.interceptor.AuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * JWT拦截器，用于验证JWT token
     * 会挂载payload到request中
     * 可用于后续的处理
     */
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**") //应用路由
                .excludePathPatterns("/auth/register"); //排除路由
    }
}
