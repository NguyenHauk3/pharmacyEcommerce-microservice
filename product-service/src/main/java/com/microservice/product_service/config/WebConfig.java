package com.microservice.product_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${app.upload-dir}")
    private String uploadDir;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // Cho phép tất cả các yêu cầu từ http://localhost:4200
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:4200")  // Chỉ cho phép yêu cầu từ localhost:4200
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Các phương thức HTTP cho phép
//                .allowedHeaders("*")  // Cho phép tất cả các header
//                .allowCredentials(true);  // Cho phép cookie nếu cần thiết
//    }
}
