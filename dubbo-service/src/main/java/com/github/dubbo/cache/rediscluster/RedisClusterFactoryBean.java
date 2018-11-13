package com.github.dubbo.cache.rediscluster;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

import com.github.dubbo.cache.CacheService;

/**
 * Create CacheService Instance (Singleton)
 * 
 * @author Administrator
 *
 */
public class RedisClusterFactoryBean implements FactoryBean<Object> {
    
    private Properties configuration;
    
    public RedisClusterFactoryBean(Properties configuration) {
        this.configuration = configuration;
    }
    
    public Object getObject()
        throws Exception {
        RedisClusterCacheServiceFactory factory = RedisClusterCacheServiceFactory.getInstance(configuration);
        return factory.createCacheService();
    }
    
    public Class<?> getObjectType() {
        return RedisClusterCacheService.class;
    }
    
    public boolean isSingleton() {
        return true;
    }
    
}
