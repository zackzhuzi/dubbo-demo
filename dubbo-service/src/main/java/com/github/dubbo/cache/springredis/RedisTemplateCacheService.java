package com.github.dubbo.cache.springredis;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.github.dubbo.cache.CacheService;
import com.github.dubbo.cache.SortedItem;

/**
 * Spring-Data-RedisTemplate Implementation
 * 
 * @author yuzhu.peng
 *
 */
public class RedisTemplateCacheService implements CacheService {
    
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    
    private Logger logger = LoggerFactory.getLogger(RedisTemplateCacheService.class);
    
    private static final int DEFAULT_LOCK_EXPIRE_TIME = 20;
    
    // key的TTL,一天
    private static final int finalDefaultTTLwithKey = 24 * 3600;
    
    @Override
    public boolean set(String key, Object value, boolean coverOnExist) {
        logger.debug("key={}|value={}|coverOnExit={}", key, value.toString(), coverOnExist);
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        if (coverOnExist) {
            opsForValue.set(key, value);
        }
        else {
            opsForValue.setIfAbsent(key, value);
        }
        return true;
    }
    
    @Override
    public boolean set(String key, Object value, int seconds) {
        logger.debug("key={}|value={}|seconds={}", key, value.toString(), seconds);
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        if (seconds > 0) {
            opsForValue.set(key, value, seconds, TimeUnit.SECONDS);
        }
        else {
            opsForValue.set(key, value);
        }
        return true;
    }
    
    @Override
    public boolean expire(String key, int seconds) {
        logger.debug("key={}|seconds={}", key, seconds);
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }
    
    @Override
    public boolean delete(String key) {
        logger.debug("key={}", key);
        redisTemplate.delete(key);
        return true;
    }
    
    @Override
    public Object get(String key) {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        Object object = opsForValue.get(key);
        return object;
    }
    
    @Override
    public long ttl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    
    @Override
    public boolean setList(String key, List<Object> list) {
        logger.debug("key={}|list={}", key, list);
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.rightPushAll(key, list) > 0;
    }
    
    @Override
    public List<Object> getList(String key) {
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        List<Object> range = opsForList.range(key, 0, -1);
        return range;
    }
    
    @Override
    public boolean setMap(String key, Map<String, Object> dataMap) {
        logger.debug("key={}|dataMap={}", key, dataMap);
        HashOperations<String, String, Object> opsForHash = redisTemplate.opsForHash();
        opsForHash.putAll(key, dataMap);
        return true;
    }
    
    @Override
    public Map<String, Object> getMap(String key) {
        HashOperations<String, String, Object> opsForHash = redisTemplate.opsForHash();
        Map<String, Object> entries = opsForHash.entries(key);
        return entries;
    }
    
    @Override
    public boolean setSet(String key, Set<Object> set) {
        logger.debug("key={}|set={}", key, set);
        BoundSetOperations<String, Object> boundSetOps = redisTemplate.boundSetOps(key);
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            boundSetOps.add(iterator.next());
        }
        return true;
    }
    
    @Override
    public Set<Object> getSet(String key) {
        BoundSetOperations<String, Object> boundSetOps = redisTemplate.boundSetOps(key);
        Set<Object> members = boundSetOps.members();
        return members;
    }
    
    @Override
    public boolean setSortedSet(String key, Set<SortedItem> sortedItems) {
        logger.debug("key={}|sortedItems={}", key, sortedItems);
        BoundZSetOperations<String, Object> boundZSetOps = redisTemplate.boundZSetOps(key);
        Iterator<SortedItem> iterator = sortedItems.iterator();
        while (iterator.hasNext()) {
            SortedItem sortedItem = iterator.next();
            boundZSetOps.add(sortedItem.getValue(), sortedItem.getScore());
        }
        return true;
    }
    
    @Override
    public Set<Object> getSortedSet(String key, long start, long end) {
        BoundZSetOperations<String, Object> boundZSetOps = redisTemplate.boundZSetOps(key);
        Set<Object> range = boundZSetOps.reverseRange(start, end);
        return range;
    }
    
    @Override
    public boolean interSectAndStore(String sourceSetKey1, Set<String> intersectSetKeys, String destKey) {
        BoundZSetOperations<String, Object> boundZSetOps = redisTemplate.boundZSetOps(sourceSetKey1);
        boundZSetOps.intersectAndStore(intersectSetKeys, destKey);
        return true;
    }
    
    @Override
    public boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }
    
    @Override
    public Object getValueFromMap(String hashKey, String param) {
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Object object = opsForHash.get(hashKey, param);
        return object;
    }
    
    @Override
    public Long hincr(String hashKey, String param, int count) {
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Long increment = opsForHash.increment(hashKey, param, count);
        return increment;
    }
    
    @Override
    public boolean lock(String lockKey, String requestId) {
        return this.lock(lockKey, requestId, DEFAULT_LOCK_EXPIRE_TIME);
    }
    
    @Override
    public boolean lock(String lockKey, String requestId, int expireSeconds) {
        logger.debug("redis lock debug, start. key:[{}], requestId:[{}], expireSeconds:[{}]",
            lockKey,
            requestId,
            expireSeconds);
        if (expireSeconds <= 0) {
            expireSeconds = DEFAULT_LOCK_EXPIRE_TIME;
        }
        
        long now = Instant.now().toEpochMilli();
        long lockExpireTime = now + expireSeconds * 1000;
        
        // SetNX
        Boolean setNx =
            redisTemplate.opsForValue().setIfAbsent(lockKey, String.valueOf(lockExpireTime) + "_" + requestId);
        logger.debug("redis lock debug, setnx. key:[{}], requestId=[{}],expireTime:[{}], executeResult:[{}]",
            lockKey,
            requestId,
            lockExpireTime,
            setNx);
        
        if (setNx) {
            // Get Lock Success
            redisTemplate.expire(lockKey, finalDefaultTTLwithKey, TimeUnit.SECONDS);
            return true;
        }
        else {
            // Get Lock Failed
            Object value = this.getKeyWithRetry(lockKey, 3);
            if (value != null) {
                // 已存在的锁超时时间
                String[] split = value.toString().split("_");
                long oldExpireTime = Long.parseLong(split[0]);
                if (oldExpireTime <= now) {
                    // 上一个锁已经过期了（但是因为某些原因并没有释放锁），当前线程直接占用该锁
                    Object andSet =
                        redisTemplate.opsForValue()
                            .getAndSet(lockKey, String.valueOf(lockExpireTime) + "_" + requestId);
                    String[] split2 = andSet.toString().split("_");
                    long currentExpireTime = Long.parseLong(split2[0]);
                    if (currentExpireTime == oldExpireTime) {
                        // 当前线程获取锁成功
                        logger.debug("redis lock debug, getSet. key:[{}], requestId:[{}],currentExpireTime:[{}], oldExpireTime:[{}], lockExpireTime:[{}]",
                            lockKey,
                            requestId,
                            currentExpireTime,
                            oldExpireTime,
                            lockExpireTime);
                        redisTemplate.expire(lockKey, finalDefaultTTLwithKey, TimeUnit.SECONDS);
                        return true;
                    }
                    else {
                        // 当前线程获取锁失败，可能别的线程抢先一步获取到该锁
                        return false;
                    }
                }
                else {
                    // 上一个锁还没有过期
                    return false;
                }
            }
            else {
                logger.warn("this lock has been released. key: [{}]", lockKey);
                return false;
            }
        }
    }
    
    @Override
    public boolean unLock(String lockKey, String requestId) {
        logger.debug("redis unlock debug, start. key:[{}], requestId:[{}].", lockKey, requestId);
        Object value = this.getKeyWithRetry(lockKey, 3);
        if (value != null) {
            // 已存在的锁超时时间
            String[] split = value.toString().split("_");
            if (split[1].equals(requestId)) {
                redisTemplate.delete(lockKey);
            }
        }
        return true;
    }
    
    private Object getKeyWithRetry(String key, int retryTimes) {
        int failTime = 0;
        while (failTime < retryTimes) {
            try {
                return redisTemplate.opsForValue().get(key);
            }
            catch (Exception e) {
                failTime++;
                if (failTime >= retryTimes) {
                    throw e;
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean lockWithTimeout(String lockKey, String requestId, int timeoutSeconds) {
        return lockWithTimeout(lockKey, requestId, DEFAULT_LOCK_EXPIRE_TIME, timeoutSeconds);
    }
    
    @Override
    public boolean lockWithTimeout(String lockKey, String requestId, int expireSeconds, int timeoutSeconds) {
        long deadline = Instant.now().toEpochMilli() + timeoutSeconds * 1000;
        while (!this.lock(lockKey, requestId, expireSeconds)) {
            if (Instant.now().toEpochMilli() > deadline) {
                return false;
            }
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
