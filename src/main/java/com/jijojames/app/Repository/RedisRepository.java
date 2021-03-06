package com.jijojames.app.Repository;

import com.jijojames.app.Model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Set;

@Repository
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private SetOperations setOperations;

    @Autowired
    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        setOperations = redisTemplate.opsForSet();
    }

    private String getKey(String project, String id) {
        return project + "/" + id;
    }

    public Long addUser(String project, String id, UserStatus userStatus) {
        return setOperations.add(getKey(project, id), userStatus.getUserId());
    }

    public Set get(String project, String id) {
        return setOperations.members(getKey(project, id));
    }

    public Long deleteUser(String project, String id, UserStatus userStatus) {
        return setOperations.remove(getKey(project, id), userStatus.getUserId());
    }

    public Boolean deleteKey(String project, String id) {
        return redisTemplate.delete(getKey(project, id));
    }
}
