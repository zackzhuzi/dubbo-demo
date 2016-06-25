package com.github.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.dubbo.service.PingService;

/**
 * 消费者
 * 
 * @author yuzhupeng
 *
 */
public class Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext-dubbo.xml");
        ctx.start();
        PingService pingService = (PingService) ctx.getBean("pingService");
        String serverIP = pingService.showServiceIp();
        System.out.println(serverIP);
    }

}
