package com.jijojames.app.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

import java.net.URISyntaxException;

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
    public RedisStandaloneConfiguration hostConfig() throws URISyntaxException {
        RedisStandaloneConfiguration hostConfig = new RedisStandaloneConfiguration();
        hostConfig.setPort(redisPort);
        hostConfig.setHostName(redisHost);
        hostConfig.setPassword(redisPassword);
        return hostConfig;
    }
}
