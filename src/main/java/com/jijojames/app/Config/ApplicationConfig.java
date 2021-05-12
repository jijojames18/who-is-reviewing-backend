package com.jijojames.app.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.Arrays;

@Configuration
public class ApplicationConfig {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    RedisConfiguration redisConfiguration;
    @Autowired
    private Environment env;

    @EventListener(ApplicationReadyEvent.class)
    public void loaded() {
        logger.info("Application loaded. Active profiles {}", Arrays.toString(env.getActiveProfiles()));
    }

    public JedisConnectionFactory getJedisConnectionFactory() {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        JedisClientConfiguration clientConfig = builder.usePooling().build();
        if (redisConfiguration.isSentinelMode()) {
            return new JedisConnectionFactory(redisConfiguration.getSentinelConfig(), clientConfig);
        } else {
            return new JedisConnectionFactory(redisConfiguration.getHostConfig(), clientConfig);
        }
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return redisTemplate;
    }
}
