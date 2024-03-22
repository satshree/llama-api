package com.llama.api.config;

import com.llama.api.middlewares.PermissionMiddleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MiddlewareConfig implements WebMvcConfigurer {
    @Autowired
    PermissionMiddleware permissionMiddleware;

    public void addInterceptors(InterceptorRegistry registry) {
        // REGISTER CUSTOM MIDDLEWARES
        registry.addInterceptor(permissionMiddleware);
    }
}
