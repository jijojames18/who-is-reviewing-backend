package com.jijojames.app.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.net.URISyntaxException;

@Profile("docker.local")
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfigDockerLocal {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigDockerLocal.class);

    @Value("${spring.application.redis-host}")
    private String redisHost;

    @Value("${spring.application.redis-port}")
    private int redisPort;

    @Value("${spring.application.redis-password}")
    private String redisPassword;

    @EventListener(ApplicationReadyEvent.class)
    public void loaded() throws URISyntaxException {
        logger.info("Application loaded in docker local mode");
    }

    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() throws URISyntaxException {
        RedisStandaloneConfiguration hostConfig = new RedisStandaloneConfiguration();
        hostConfig.setPort(this.redisPort);
        hostConfig.setHostName(this.redisHost);
        hostConfig.setPassword(this.redisPassword);
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        JedisClientConfiguration clientConfig = builder.usePooling().build();
        return new JedisConnectionFactory(hostConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() throws URISyntaxException {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(getJedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }
}
