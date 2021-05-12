package com.jijojames.app.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.net.URI;
import java.net.URISyntaxException;

@Profile("heroku")
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfigHeroku {

    @Autowired
    private Environment env;

    @Value("${spring.application.redis-url-env}")
    private String redisUrlEnv;

    private String getRedisUrl() {
        return env.getProperty(redisUrlEnv);
    }

    @Bean
    public RedisConfiguration redisConfiguration() throws URISyntaxException {
        URI redisUri = new URI(getRedisUrl());
        RedisConfiguration redisConfiguration = new RedisConfiguration(redisUri.getHost(), redisUri.getPort());
        redisConfiguration.setPassword(redisUri.getUserInfo().split(":", 2)[1]);
        return redisConfiguration;
    }
}
