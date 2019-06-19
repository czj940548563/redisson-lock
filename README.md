### redisson 分布式锁

> RedissonManager.class
实例化Redisson
```

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

```
> DistributedRedisLoc.class
加锁和释放锁

```
 public static boolean acquire(String lockName){
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        mylock.lock(2, TimeUnit.MINUTES); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
        System.err.println("======lock======"+Thread.currentThread().getName());
        return  true;
    }

    public static void release(String lockName){
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        mylock.unlock();
        System.err.println("======unlock======"+Thread.currentThread().getName());
    }
```

> 测试

```
  @RequestMapping("/test")
    public String redder() throws IOException {
        String key = "test123";
        DistributedRedisLock.acquire(key);

        //需要加锁的业务
        Long result =  RedissonManager.nextID();

        DistributedRedisLock.release(key);
        return ""+result;
    }
```
> 单机500并发测试 值501 数据正确
![image](https://github.com/czj940548563/images/blob/master/1560924780176.png?raw=true)


