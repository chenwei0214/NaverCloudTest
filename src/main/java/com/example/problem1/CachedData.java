package com.example.problem1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Project Name: NaverCloudTest
 * Package Name: com.example.problem1
 * Date: 2022/4/7 14:06
 * Created by: wei.chen
 */
public class CachedData {

    private final static Map<String, Object> cacheData = new HashMap<String, Object>();//构造缓存对象
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();//构造读写锁

    public Object processCachedData(String key) {
        Object value = null;
        try {
            //当线程开始读时，首先开始加上读锁
            readWriteLock.readLock().lock();
            value = cacheData.get(key);
            if (value == null) {
                try {
                    //在开始写之前，首先要释放读锁，否则写锁无法拿到
                    readWriteLock.readLock().unlock();
                    readWriteLock.writeLock().lock();
                    value = cacheData.get(key);
                    if (value == null) {
                        value = "resultData";
                        System.out.println("load data:" + key);
                        cacheData.put(key, value);
                    }
                    //写完之后重入降级为读锁
                    readWriteLock.readLock().lock();
                } finally {
                    //最后释放写锁
                    readWriteLock.writeLock().unlock();
                }
            }
        } finally {
            //释放读锁
            readWriteLock.readLock().unlock();
        }
        return value;
    }

    public static void main(String[] args) {
        final CachedData cachedData = new CachedData();
        //开启20个线程同时去获取10个相同的key
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    for (int j = 1; j <= 10; j++) {
                        cachedData.processCachedData("key" + j);
                    }
                }
            });
        }

        executorService.shutdown();
    }
}
