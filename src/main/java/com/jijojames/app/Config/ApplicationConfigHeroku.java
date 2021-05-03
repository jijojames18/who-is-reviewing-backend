package com.jijojames.app.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.net.URI;
import java.net.URISyntaxException;

@Profile("heroku")
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfigHeroku {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigHeroku.class);

    @Autowired
    private Environment env;

    @Value("${spring.application.redis-url}")
    private String RedisUrl;

    @EventListener(ApplicationReadyEvent.class)
    public void loaded() throws URISyntaxException {
        logger.info("Application loaded in heroku mode");
    }

    private String getRedisUrl() {
        return env.getProperty(RedisUrl);
    }

    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() throws URISyntaxException {
        URI redisUri = new URI(getRedisUrl());
        RedisStandaloneConfiguration hostConfig = new RedisStandaloneConfiguration();
        hostConfig.setPort(redisUri.getPort());
        hostConfig.setHostName(redisUri.getHost());
        hostConfig.setPassword(redisUri.getUserInfo().split(":", 2)[1]);
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
