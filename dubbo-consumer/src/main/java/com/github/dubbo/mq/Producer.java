package com.github.dubbo.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Producer
 * 
 * @author yuzhu.peng
 */
public class Producer {
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    /**
     * Send to named destination
     * 
     * @param destinationName
     * @param messageCreator
     */
    public void sendMessage(String destinationName, MessageCreator messageCreator) {
        if (null == destinationName) {
            jmsTemplate.send(messageCreator);
            return;
        }
        jmsTemplate.send(destinationName, messageCreator);
    }
    
}
