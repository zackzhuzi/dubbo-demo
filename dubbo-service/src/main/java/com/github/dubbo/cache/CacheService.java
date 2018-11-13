package com.github.dubbo.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheService {
    
    /**
     * 存储value，并提供是否覆盖选项
     * 
     * @param key
     * @param value
     * @param coverOnExist
     */
    public boolean set(String key, Object value, boolean coverOnExist);
    
    /**
     * 存储value，并提供是否覆盖选项
     * 
     * @param key
     * @param value
     * @param coverOnExist
     */
    public boolean set(String key, Object value, int seconds);
    
    /**
     * 为key设置超时时间，单位秒
     * 
     * @param key
     * @return
     */
    public boolean expire(String key, int seconds);
    
    /**
     * 根据key删除对象
     * 
     * @param key
     * @return
     */
    public boolean delete(String key);
    
    /**
     * 根据key和对象类型获取对象
     * 
     * @param key
     * @param objectType
     * @return
     */
    public Object get(String key);
    
    /**
     * 返回指定key的剩余有效时间
     * 
     * @param key
     * @return
     */
    public long ttl(String key);
    
    /**
     * 存储list
     * 
     * @param key
     * @param list
     * @param seconds
     * @return
     */
    public boolean setList(String key, List<Object> list);
    
    /**
     * 获取list
     * 
     * @param key
     * @param objectType
     * @return
     */
    public List<Object> getList(String key);
    
    /**
     * 保存map
     * 
     * @param key
     * @param dataMap
     */
    public boolean setMap(String key, Map<String, Object> dataMap);
    
    public Map<String, Object> getMap(String key);
    
    public boolean setSet(String key, Set<Object> set);
    
    public Set<Object> getSet(String key);
    
    public boolean setSortedSet(String key, Set<SortedItem> sortedItems);
    
    public Set<Object> getSortedSet(String key, long start, long end);
    
    public boolean interSectAndStore(String sourceSetKey, Set<String> intersectSetKeys, String destKey);
    
    public boolean exist(String key);
    
    public Object getValueFromMap(String hashKey, String param);
    
    /**
     * 
     * @param hashKey
     * @param param
     * @param count
     * @return the final count of the param after this operation
     */
    public Long hincr(String hashKey, String param, int count);
    
    public boolean lock(String lockKey, String requestId, int expireSeconds);
    
    public boolean lock(String lockKey, String requestId);
    
    public boolean lockWithTimeout(String lockKey, String requestId, int timeoutSeconds);
    
    public boolean lockWithTimeout(String lockKey, String requestId, int expireSeconds, int timeoutSeconds);
    
    public boolean unLock(String lockKey, String requestId);
}
