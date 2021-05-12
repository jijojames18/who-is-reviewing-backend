package com.jijojames.app.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Profile("docker.prod")
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfigDockerProd {
    @Value("${spring.application.redis-master-set}")
    private String redisMasterSet;

    @Value("${spring.application.redis-sentinel-host}")
    private String redisHost;

    @Value("${spring.application.redis-sentinel-port}")
    private int redisPort;

    @Value("${spring.application.redis-password}")
    private String redisPasswordFile;

    public String getRedisPassword() {
        Resource resource = new FileSystemResource("/run/secrets/" + redisPasswordFile);
        String dbPassword = "";
        if (resource.exists()) {
            try {
                dbPassword = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return dbPassword;
    }

    @Bean
    public RedisConfiguration redisConfig() {
        RedisConfiguration redisConfig = new RedisConfiguration(redisHost, redisPort);
        redisConfig.setMasterSetName(redisMasterSet);
        redisConfig.setPassword(getRedisPassword());
        return redisConfig;
    }
}
