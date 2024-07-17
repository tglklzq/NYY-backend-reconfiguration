package org.lzq.nyybackend.config;


import org.lzq.nyybackend.interceptor.JwtTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;

    @Autowired
    public WebMvcConfig(JwtTokenInterceptor jwtTokenInterceptor) {
        this.jwtTokenInterceptor = jwtTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，并设置拦截的路径（这里设置为除了 /api/user/login 外的所有路径）
        registry.addInterceptor(jwtTokenInterceptor)
                .excludePathPatterns("/api/user/login") // 排除登录接口
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/user/logout")
                .excludePathPatterns("/api/user/refreshToken")
                .addPathPatterns("/api/user/**"); // 拦截所有 /api/user/** 下的路径
    }
}
