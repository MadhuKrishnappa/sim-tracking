package com.sim.tracking.cache.impl;

import com.sim.tracking.cache.ICache;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class CacheImpl implements ICache {

    @Autowired
    private RedissonClient redissonClient;

    public static final long EXPIRY_TIME = 2;

    private static Logger logger = LoggerFactory.getLogger(CacheImpl.class);

    @Override
    public void put(String key, Object object) {
        {
            try {
                RMapCache<String, Object> rmap = redissonClient.getMapCache(key);
                rmap.put(key, object, EXPIRY_TIME, TimeUnit.DAYS);
            } catch (Exception exp) {
                logger.error("ErrorPuttingCacheKey: {}, Exception: {}", key, exp);
            }
        }
    }

    @Override
    public Object get(String key) {
        try {
            RMapCache<String, Object> rmap = redissonClient.getMapCache(key);
            Object object = rmap.get(key);
            if (object != null) {
                return object;
            }
        } catch (Exception exp) {
            delete(key);
            logger.error("ErrorGettingCacheKey: {}, Exception: {}", key, exp);
        }

        return null;
    }

    @Override
    public void delete(String key) {
        try {
            RMapCache<String, Object> rmap = redissonClient.getMapCache(key);
            rmap.remove(key);
        } catch (Exception exp) {
            logger.error("ErrorDeletingCacheKey: {}, Exception: {}", key, exp);
        }
    }


    @Override
    public void put(String key, Object object, int daysLessThan30) throws Exception {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, daysLessThan30);

        Date shouldExpire = cal.getTime();
        try {
            RMapCache<String, Object> rmap = redissonClient.getMapCache(key);
            rmap.put(key, object, (shouldExpire.getTime() - new Date().getTime()), TimeUnit.MILLISECONDS);
        } catch (Exception exp) {
            logger.error("ErrorPuttingCacheKey: {}, Exception: {}", key, exp);
        }
    }
}
