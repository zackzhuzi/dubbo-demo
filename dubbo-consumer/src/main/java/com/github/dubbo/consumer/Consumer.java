package com.github.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.dubbo.service.PingService;

/**
 * 消费者
 * 
 * @author yuzhupeng
 */
public class Consumer {
//    @Autowired
//    @Reference(version="1.0")
//    private PingService pingService;

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-dubbo.xml");
        ctx.start();
        PingService pingService = (PingService) ctx.getBean("pingService"); // 配置文件中定义的spring
                                                                            // Bean。Customer无具体实现，dubbo配置会默认生成一个spring
                                                                            // bean实例，作为代理来处理dubbo请求
        String serverIP = pingService.showServiceIp();
        System.out.println(serverIP);
    }

}
