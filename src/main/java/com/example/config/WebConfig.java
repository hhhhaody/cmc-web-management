package com.example.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类
 * 该类用于配置跨域请求，允许来自http://10.200.20.17:5173的请求访问这个应用
 */
@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        // 创建并返回一个WebMvcConfigurer对象，以允许进行CORS（跨源资源共享）配置
        return new WebMvcConfigurer() {

            // 重写addCorsMappings方法添加CORS配置
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 对所有路径（"/**"）进行CORS配置
                registry.addMapping("/**")
                        // 允许来自 "http://10.200.20.17:5173" 的跨域请求
//                        .allowedOrigins("http://10.10.205.58:5173")
                        .allowedOrigins("http://10.200.20.17:5173", "http://10.10.205.58:5173","http://localhost:5173")
                        // 允许的HTTP方法（GET、POST、PUT、DELETE、HEAD、OPTIONS）
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                        // 允许的HTTP头
                        .allowedHeaders("*")
                        // 是否允许请求带有验证信息
                        .allowCredentials(true);
            }
        };
    }
}

