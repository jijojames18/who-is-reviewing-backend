package com.jijojames.app;

import com.jijojames.app.Enum.PRStatus;
import com.jijojames.app.Model.UserStatus;
import com.jijojames.app.Repository.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class RestEndPointController {
    private static final Logger logger = LoggerFactory.getLogger(RestEndPointController.class);

    @Autowired
    private RedisRepository redisRepository;

    @GetMapping("/{project}/{id}")
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<Object> getById(@PathVariable String project, @PathVariable String id) {
        logger.debug("[START] Received new request to fetch data for project {} and pull request {}", project, id);
        Set userList = redisRepository.get(project, id);
        logger.debug("[END] Request processed");
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/{project}/{id}")
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<Object> setById(@PathVariable String project, @PathVariable String id, @RequestBody UserStatus userStatus) {
        logger.debug("[START] Received new request to set data for project {} and pull request {}", project, id);
        if (userStatus.getStatus() == PRStatus.STARTED) {
            redisRepository.set(project, id, userStatus);
        } else {
            redisRepository.deleteUser(project, id, userStatus);
        }
        logger.debug("[END] Request processed");
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/{project}/{id}")
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<Object> deleteById(@PathVariable String project, @PathVariable String id) {
        logger.debug("[START] Received new request to delete data for project {} and pull request {}", project, id);
        redisRepository.deleteKey(project, id);
        logger.debug("[END] Request processed");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
