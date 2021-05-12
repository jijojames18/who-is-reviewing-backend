package com.jijojames.app.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"dev", "docker.local"})
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfigDev {

    @Value("${spring.application.redis-host}")
    private String redisHost;

    @Value("${spring.application.redis-port}")
    private int redisPort;

    @Value("${spring.application.redis-password}")
    private String redisPassword;

    @Bean
    public RedisConfiguration redisConfiguration() {
        RedisConfiguration redisConfiguration = new RedisConfiguration(redisHost, redisPort);
        redisConfiguration.setPassword(redisPassword);
        return redisConfiguration;
    }
}
