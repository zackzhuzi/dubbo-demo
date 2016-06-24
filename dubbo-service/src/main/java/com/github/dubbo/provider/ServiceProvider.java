package com.github.dubbo.provider;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动服务提供者
 * 
 * @author yuzhupeng
 *
 */
public class ServiceProvider {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext-dubbo.xml");
        ctx.start();
        System.out.println("服务提供者在注册中心暴露服务");
        try {
            // 为保证服务一直开着，利用输入流的阻塞来模拟
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
