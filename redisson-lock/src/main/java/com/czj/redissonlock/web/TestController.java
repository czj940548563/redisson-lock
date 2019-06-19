package com.czj.redissonlock.web;

import com.czj.redissonlock.lock.DistributedRedisLock;
import com.czj.redissonlock.lock.RedissonManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: clownc
 * @Date: 2019-06-19 10:49
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String redder() throws IOException {
        String key = "test123";
        DistributedRedisLock.acquire(key);


        Long result =  RedissonManager.nextID();

        DistributedRedisLock.release(key);
        return ""+result;
    }
}
