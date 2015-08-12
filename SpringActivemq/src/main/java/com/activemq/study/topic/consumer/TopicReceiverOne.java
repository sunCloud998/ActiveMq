package com.activemq.study.topic.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 使用和spring集成的方式创建发布订阅消息接收者
 * @Description: TopicReceiver.java
 * @Author: sunfayun
 * @Date: 2015/08/05
 * @Time: 下午8:43
 * @Version 1.0
 */
@Service
public class TopicReceiverOne implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(TopicReceiverOne.class);

    @Override
    public void onMessage(Message message) {
        try {
            String messageInfo = ((TextMessage)message).getText();
            logger.info("发布订阅消费者【1】接收到消息:{}",messageInfo);
        } catch (JMSException e) {
            logger.error("JMSException:",e);
        }
    }

}
