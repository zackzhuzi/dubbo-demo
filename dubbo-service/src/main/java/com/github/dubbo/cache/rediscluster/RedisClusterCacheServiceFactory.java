package com.github.dubbo.cache.rediscluster;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisClusterCacheServiceFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisClusterCacheServiceFactory.class);
    
    private JedisCluster jc;
    
    private String cacheOn;
    
    public static RedisClusterCacheServiceFactory getInstance(Properties config) {
        return new RedisClusterCacheServiceFactory(config);
    }
    
    private RedisClusterCacheServiceFactory(Properties config) {
        if (config == null) {
            config = new Properties();
        }
        init(config);
    }
    
    private void init(Properties config) {
        String servers = config.getProperty("servers");
        if (StringUtils.isEmpty(servers)) {
            return;
        }
        
        String[] serverArr = servers.split(";");
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        for (String serverStr : serverArr) {
            if (!StringUtils.isEmpty(serverStr) && serverStr.indexOf(":") != -1) {
                String[] ipAndPort = serverStr.split(":");
                if (ipAndPort != null && ipAndPort.length > 1) {
                    String ip = ipAndPort[0];
                    String port = ipAndPort[1];
                    jedisClusterNodes.add(new HostAndPort(ip, Integer.parseInt(port)));
                }
            }
        }
        
        String maxWaitMillis = config.getProperty("redis.maxWaitMillis");
        String maxTotal = config.getProperty("redis.maxTotal");
        String minIdle = config.getProperty("redis.minIdle");
        String maxIdle = config.getProperty("redis.maxIdle");
        String testOnBorrow = config.getProperty("redis.testOnBorrow");
        String testOnReturn = config.getProperty("redis.testOnReturn");
        
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
        poolConfig.setMaxTotal(Integer.parseInt(maxTotal));
        poolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        poolConfig.setMinIdle(Integer.parseInt(minIdle));
        if ("true".equals(testOnBorrow)) {
            poolConfig.setTestOnBorrow(true);
        }
        else {
            poolConfig.setTestOnBorrow(false);
        }
        if ("true".equals(testOnReturn)) {
            poolConfig.setTestOnReturn(true);
        }
        else {
            poolConfig.setTestOnReturn(false);
        }
        
        String connectionTimeout = config.getProperty("redis.connectionTimeout");
        String soTimeout = config.getProperty("redis.cluster.soTimeout");
        String maxRedirections = config.getProperty("redis.cluster.maxRedirections");
        
        cacheOn = config.getProperty("cache.on");
        
        jc =
            new JedisCluster(jedisClusterNodes, Integer.parseInt(connectionTimeout), Integer.parseInt(soTimeout),
                Integer.parseInt(maxRedirections), poolConfig);
        logger.info("JedisCluster Init Done");
    }
    
    public RedisClusterCacheService createCacheService() {
        try {
            RedisClusterCacheService cacheService = new RedisClusterCacheService();
            cacheService.setJc(jc);
            cacheService.setCacheOn(cacheOn);
            return cacheService;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
