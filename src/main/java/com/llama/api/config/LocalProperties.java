package com.llama.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.local.properties")
public class LocalProperties {
    private final Environment env;

    public LocalProperties(Environment env) {
        this.env = env;
    }

    // Example method to access a local property
    public String getLocalProperty(String propertyName) {
        return env.getProperty(propertyName);
    }
}
