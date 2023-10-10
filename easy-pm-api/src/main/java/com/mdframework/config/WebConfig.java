package com.mdframework.config;

import com.mdframework.aspectj.CurrentMemberMethodArgumentResolver;
import com.mdframework.module.interceptor.AuthInterceptor;
import com.mdframework.module.member.service.IMemberInfoService;
import com.mdframework.module.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * web配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private IMemberInfoService memberInfoService;

    @Autowired
    private TokenService tokenService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns( "/**")
//                .excludePathPatterns( "/**")
                .excludePathPatterns("/outer/**")
                .excludePathPatterns("/swagger**")
                .excludePathPatterns("/course/courseCategory/**")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/error")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/common/upload")
                .excludePathPatterns("/file/**")
                ;
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor(memberInfoService,tokenService);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .maxAge(3600)
//                .allowedMethods("*");
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userResolver());
    }

    @Bean
    public CurrentMemberMethodArgumentResolver userResolver() {
        return new CurrentMemberMethodArgumentResolver();
    }
}
