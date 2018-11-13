package com.github.dubbo.cache.rediscluster;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
import com.github.dubbo.cache.CacheService;
import com.github.dubbo.cache.SortedItem;

public class RedisClusterCacheService implements CacheService {
    
    public static final String CACHE_ON = "1";
    
    public static final String CACHE_OFF = "0";
    
    private static final Logger logger = LoggerFactory.getLogger(RedisClusterCacheService.class);
    
    private JedisCluster jc;
    
    private String cacheOn;
    
    // 只在键不存在时，才对键进行设置操作
    private static final String NX = "NX";
    
    // 只在键已经存在时，才对键进行设置操作
    @SuppressWarnings("unused")
    private static final String XX = "XX";
    
    // 时间单位秒
    private static final String EX = "EX";
    
    public String getCacheOn() {
        return cacheOn;
    }
    
    public void setCacheOn(String cacheOn) {
        this.cacheOn = cacheOn;
    }
    
    public JedisCluster getJc() {
        return jc;
    }
    
    public void setJc(JedisCluster jc) {
        this.jc = jc;
    }
    
    /**
     * 存储value，并提供是否覆盖选项
     * 
     * @param key
     * @param value
     * @param coverOnExist
     */
    public boolean set(String key, Object value, boolean coverOnExist) {
        logger.debug("key :" + key + " | value:" + value + " | coverOnExist:" + coverOnExist);
        if (StringUtils.isEmpty(key))
            return false;
        
        String result = "";
        
        if (coverOnExist)
            if ("java.lang.String".equals(value.getClass().getName()))
                result = jc.set(key, value.toString());
            else
                result = jc.set(key, JSON.toJSONString(value));
        else if ("java.lang.String".equals(value.getClass().getName()))
            result = String.valueOf(jc.setnx(key, value.toString()));
        else
            result = String.valueOf(jc.setnx(key, JSON.toJSONString(value)));
        
        if (StringUtils.isEmpty(result)) {
            return false;
        }
        else {
            if ("1".equals(result))
                result = "OK";
            if ("OK".equals(result.toUpperCase())) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    
    /**
     * 覆盖存储value，并设置过期时间，单位秒
     * 
     * @param key
     * @param value
     * @param second
     */
    public boolean set(String key, Object value, int second) {
        if (StringUtils.isEmpty(key))
            return false;
        logger.debug("opt:setwithtime | key :" + key + " | value:" + value + " | second:" + second);
        
        String result = "";
        if (jc.exists(key)) {
            if ("java.lang.String".equals(value.getClass().getName())) {
                if (second <= 0) {
                    result = jc.set(key, value.toString());
                }
                else {
                    result = jc.setex(key, second, value.toString());
                }
            }
            else {
                if (second <= 0) {
                    result = jc.set(key, JSON.toJSONString(value));
                }
                else {
                    result = jc.setex(key, second, JSON.toJSONString(value));
                }
            }
        }
        else {
            if ("java.lang.String".equals(value.getClass().getName())) {
                if (second <= 0) {
                    result = jc.set(key, value.toString());
                }
                else {
                    result = jc.set(key, value.toString(), NX, EX, new Long(second).longValue());
                }
            }
            else {
                if (second <= 0) {
                    result = jc.set(key, JSON.toJSONString(value));
                }
                else {
                    result = jc.set(key, JSON.toJSONString(value), NX, EX, new Long(second).longValue());
                }
            }
        }
        if (StringUtils.isEmpty(result)) {
            return false;
        }
        else {
            if ("OK".equals(result.toUpperCase())) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    
    /**
     * 为key设置超时时间，单位秒
     * 
     * @param key
     * @return
     */
    public boolean expire(String key, int seconds) {
        return jc.expire(key, seconds) == 1;
    }
    
    /**
     * 根据key删除对象
     * 
     * @param key
     * @return
     */
    public boolean delete(String key) {
        return jc.del(key).longValue() > 0;
    }
    
    /**
     * 根据key和对象类型获取对象
     * 
     * @param key
     * @param objectType
     * @return
     */
    public Object get(String key) {
        try {
            if (!CACHE_ON.equals(this.getCacheOn()))
                return null;
            if (StringUtils.isEmpty(key))
                return null;
            String result = jc.get(key);
            return result;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
        
    }
    
    /**
     * 返回指定key的剩余有效时间
     * 
     * @param key
     * @return
     */
    public long ttl(String key) {
        return jc.ttl(key);
    }
    
    @Override
    public boolean setList(String key, List<Object> list) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public List<Object> getList(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean setMap(String key, Map<String, Object> dataMap) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public Map<String, Object> getMap(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean setSet(String key, Set<Object> set) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public Set<Object> getSet(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean setSortedSet(String key, Set<SortedItem> sortedItems) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public Set<Object> getSortedSet(String key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean interSectAndStore(String sourceSetKey, Set<String> intersectSetKeys, String destKey) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean exist(String key) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public Object getValueFromMap(String hashKey, String param) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Long hincr(String hashKey, String param, int count) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean lock(String lockKey, String requestId, int seconds) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean unLock(String lockKey, String requestId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean lock(String lockKey, String requestId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean lockWithTimeout(String lockKey, String requestId, int timeoutSeconds) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean lockWithTimeout(String lockKey, String requestId, int expireSeconds, int timeoutSeconds) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
