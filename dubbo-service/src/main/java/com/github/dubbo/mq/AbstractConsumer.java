package com.github.dubbo.mq;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public abstract class AbstractConsumer extends DefaultMessageListenerContainer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(AbstractConsumer.class);

    public AbstractConsumer(){
        super();
        setMessageListener(this);
    }

    public abstract void handleMessgage(Message message) throws Exception;

    @Override
    public void onMessage(Message paramMessage) {
        try {
            handleMessgage(paramMessage);
            logger.info("AMQ consume success");
        } catch (Exception e) {
            logger.error("AMQ consume fail");
            e.printStackTrace();
        }
    }

}
