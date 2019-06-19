package com.czj.redissonlock.lock;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.config.Config;

/**
 * @Author: clownc
 * @Date: 2019-06-19 10:43
 */
public class RedissonManager {
    private static final String RAtomicName = "genId_";


    private static Config config = new Config();
    //声明redisso对象
    private static Redisson redisson = null;
    //实例化redisson
    static{
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        //得到redisson对象
        redisson = (Redisson) Redisson.create(config);
        //清空自增的ID数字
        RAtomicLong atomicLong = redisson.getAtomicLong(RAtomicName);
        long pValue=1;
        atomicLong.set(pValue);
    }

    //获取redisson对象的方法
    public static Redisson getRedisson(){
        return redisson;
    }

    /** 获取redis中的原子ID */
    public static Long nextID(){
        RAtomicLong atomicLong = getRedisson().getAtomicLong(RAtomicName);
        //原子性的获取下一个ID，递增1
        atomicLong.incrementAndGet();
        return atomicLong.get();
    }

}
