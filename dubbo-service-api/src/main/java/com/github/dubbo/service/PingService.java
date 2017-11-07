package com.github.dubbo.service;

import model.Response;

/**
 * Hello World
 * 
 * @author yuzhupeng
 *
 */
public interface PingService {
    /**
     * 打印服务提供者IP
     */
    public Response showServiceIp();
}