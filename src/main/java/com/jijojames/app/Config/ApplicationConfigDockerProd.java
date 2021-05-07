package com.jijojames.app.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Profile("docker.prod")
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfigDockerProd {

    @Autowired
    private Environment env;

    @Value("${spring.application.redis-sentinel}")
    private String redisHost;

    @Value("${spring.application.redis-sentinel-port}")
    private String redisPortEnv;

    @Value("${spring.application.redis-password}")
    private String redisPassword;

    public int getRedisPort() {
        return Integer.parseInt(env.getProperty(redisPortEnv));
    }

    @Bean
    public RedisConfiguration redisConfig() {
        RedisConfiguration redisConfig = new RedisConfiguration(redisHost, getRedisPort());
        redisConfig.setIsSentinel(true);
        redisConfig.setPassword(redisPassword);
        return redisConfig;
    }
}
