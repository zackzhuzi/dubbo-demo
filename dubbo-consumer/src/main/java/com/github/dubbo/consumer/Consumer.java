package com.github.dubbo.consumer;

import java.io.IOException;

import model.Response;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.dubbo.service.PingService;

/**
 * 消费者
 * 
 * @author yuzhupeng
 */
public class Consumer {

    // @Autowired
    // @Reference(version="1.0")
    // private PingService pingService;

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-dubbo.xml");
        ctx.start();
        PingService pingService = (PingService) ctx.getBean("pingService"); // 配置文件中定义的spring
                                                                            // Bean。Customer无具体实现，dubbo配置会默认生成一个spring
                                                                            // bean实例，作为代理来处理dubbo请求
        Response serverIP = pingService.showServiceIp();
        System.out.println(serverIP.getName() + serverIP.getIpAdrress());
        try {
            // 为保证服务一直开着，利用输入流的阻塞来模拟
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
