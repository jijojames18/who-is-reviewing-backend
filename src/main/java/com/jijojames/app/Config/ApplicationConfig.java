package com.jijojames.app.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfig {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private Environment env;

    @Value("${spring.application.redis-url}")
    private String RedisUrl;

    @EventListener(ApplicationReadyEvent.class)
    public void loaded() {
        logger.info("Application loaded");
    }

    public String getRedisUrl() {
        return env.getProperty(RedisUrl);
    }
}
