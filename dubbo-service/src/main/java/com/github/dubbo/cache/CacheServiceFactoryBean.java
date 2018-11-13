package com.github.dubbo.cache;

import javax.annotation.Resource;

import org.springframework.beans.factory.FactoryBean;

public class CacheServiceFactoryBean implements FactoryBean<CacheService> {
    // @Resource(name = "redisClusterCacheService")
    // private CacheService redisClusterCacheService;
    
    @Resource(name = "redisTemplateCacheService")
    private CacheService redisTemplateCacheService;
    
    private int mode;
    
    public CacheServiceFactoryBean(int mode) {
        this.mode = mode;
    }
    
    @Override
    public CacheService getObject()
        throws Exception {
        if (mode == 1) {
            return redisTemplateCacheService;
        }
        //
        // if (mode == 2) {
        // return redisClusterCacheService;
        // }
        return null;
    }
    
    @Override
    public Class<?> getObjectType() {
        return CacheService.class;
    }
    
    @Override
    public boolean isSingleton() {
        return true;
    }
}
