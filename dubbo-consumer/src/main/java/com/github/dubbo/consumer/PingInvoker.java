package com.github.dubbo.consumer;

import java.io.IOException;

import model.Response;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.github.dubbo.facade.PingFacade;

/**
 * 消费者
 * 
 * @author yuzhupeng
 */
public class PingInvoker {

    // @Autowired
    // @Reference(version="1.0")
    // private PingService pingService;

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-dubbo.xml");
        ctx.start();
        PingFacade pingService = (PingFacade) ctx.getBean("pingFacade"); // 配置文件中定义的spring
                                                                         // Bean。Customer无具体实现，dubbo配置会默认生成一个spring
                                                                         // bean实例，作为代理来处理dubbo请求
        // Echo Test
        EchoService echoService = (EchoService) pingService;
        System.out.println(echoService.$echo("hello there"));

        boolean consumerSide = RpcContext.getContext().isConsumerSide();
        if (consumerSide) {
            System.out.println(RpcContext.getContext().getRemoteHost());
        }

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
