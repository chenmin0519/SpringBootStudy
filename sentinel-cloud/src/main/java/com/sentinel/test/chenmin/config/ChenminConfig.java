package com.sentinel.test.chenmin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix  = "chenmin")
public class ChenminConfig {
    private String url;
    private String name;
}
