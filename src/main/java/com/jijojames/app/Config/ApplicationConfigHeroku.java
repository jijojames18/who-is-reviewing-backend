package com.jijojames.app.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

import java.net.URI;
import java.net.URISyntaxException;

@Profile("heroku")
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfigHeroku {

    @Autowired
    private Environment env;

    @Value("${spring.application.redis-url}")
    private String redisUrl;

    private String getRedisUrl() {
        return env.getProperty(redisUrl);
    }

    @Bean
    public RedisStandaloneConfiguration hostConfig() throws URISyntaxException {
        URI redisUri = new URI(getRedisUrl());
        RedisStandaloneConfiguration hostConfig = new RedisStandaloneConfiguration();
        hostConfig.setPort(redisUri.getPort());
        hostConfig.setHostName(redisUri.getHost());
        hostConfig.setPassword(redisUri.getUserInfo().split(":", 2)[1]);
        return hostConfig;
    }
}
