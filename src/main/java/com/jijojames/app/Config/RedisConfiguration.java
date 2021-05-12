package com.jijojames.app.Config;

import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

public class RedisConfiguration {
    private boolean isSentinel = false;

    private String hostName;

    private int port;

    private String password;

    private String masterSetName;

    public RedisConfiguration() {
        this.hostName = "localhost";
        this.port = 6379;
        this.password = "";
    }

    public RedisConfiguration(String hostName) {
        this.hostName = hostName;
        this.port = 6379;
        this.password = "";
    }

    public RedisConfiguration(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
        this.password = "";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isSentinelMode() {
        return isSentinel;
    }

    public String getMasterSetName() {
        return this.masterSetName;
    }

    public void setMasterSetName(String masterSetName) {
        this.masterSetName = masterSetName;
        this.isSentinel = true;
    }

    public RedisStandaloneConfiguration getHostConfig() {
        RedisStandaloneConfiguration hostConfig = new RedisStandaloneConfiguration();
        hostConfig.setPort(port);
        hostConfig.setHostName(hostName);
        hostConfig.setPassword(password);
        return hostConfig;
    }

    public RedisSentinelConfiguration getSentinelConfig() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
        sentinelConfig.setMaster(masterSetName);
        sentinelConfig.sentinel(hostName, port);
        sentinelConfig.setPassword(password);
        return sentinelConfig;
    }
}
