package com.github.dubbo.facade;

import model.Response;

/**
 * Hello World
 * 
 * @author yuzhupeng
 */
public interface PingFacade {

    /**
     * 打印服务提供者IP
     */
    public Response showServiceIp();
}
