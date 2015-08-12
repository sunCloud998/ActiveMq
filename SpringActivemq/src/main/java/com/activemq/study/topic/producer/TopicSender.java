package com.activemq.study.topic.producer;

import com.activemq.study.domain.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 使用和spring集成的方式创建发布订阅消息发送者
 * @Description: TopicSender.java
 * @Author: sunfayun
 * @Date: 2015/08/05
 * @Time: 下午9:12
 * @Version 1.0
 */
@Service
public class TopicSender {

    @Autowired
    @Qualifier("jmsTopicTemplate")
    private JmsTemplate jmsTemplate;
    private Gson gson = new Gson();

    /**
     * 发布订阅消息发送者
     * @param topicName 主题名称
     * @param user 消息内容
     */
    public void send(String topicName, final User user){
        final String message = gson.toJson(user);
        jmsTemplate.send(topicName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

}
