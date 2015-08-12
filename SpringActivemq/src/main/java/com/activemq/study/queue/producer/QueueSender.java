package com.activemq.study.queue.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 使用和spring集成的方式创建点对点消息发送者
 * @Description: QueueSender.java
 * @Author: sunfayun
 * @Date: 2015/08/05
 * @Time: 下午6:00
 * @Version 1.0
 */
@Service
public class QueueSender {

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 发送消息到指定队列
     * @param queueName 队列名称
     * @param message 消息内容
     */
    public void send(String queueName, final String message){
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

}
