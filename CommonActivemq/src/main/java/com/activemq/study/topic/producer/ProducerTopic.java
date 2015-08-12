package com.activemq.study.topic.producer;

import com.activemq.study.domain.User;
import com.activemq.study.util.ActivemqUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.List;

/**
 * 发布订阅消息生产者
 * @Description: ProducerTopic.java
 * @Author: sunfayun
 * @Date: 2015/08/12
 * @Time: 下午2:52
 * @Version 1.0
 */
public class ProducerTopic {

    private static final Logger logger = LoggerFactory.getLogger(ProducerTopic.class);
    private ActivemqUtil activemqUtil = ActivemqUtil.getInstance();
    private static final String subject = "test.topic";
    private Gson gson = new Gson();

    public static void main(String[] args) {
        ProducerTopic producerTopic = new ProducerTopic();
        producerTopic.sendMessage();
    }

    /**
     * 发布订阅向MQ发送消息
     * 1.初始化连接工厂ConnectionFactory
     * 2.创建连接Connection
     * 3. 创建会话session
     * 4.创建topic
     * 5.获得消息生产者MessageProducer
     * 6.使用消息生产者发送消息
     * 7. 关闭会话session和连接Connection
     * 只有那些在线的订阅者可以收到消息，需要先启动Subscriber
     */
    public void sendMessage(){
        try {
            //初始化连接工厂，并创建连接
            Connection connection = activemqUtil.createConnection();
            //打开连接
            connection.start();
            //创建会话
            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //创建要发布的主题，和Queue的区别就在此
            Destination destination = session.createTopic(subject);
            //创建消息生产者
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();
            List<String> data = this.buildData();
            for(String msg : data){
                message.setText(msg);
                //发送消息
                producer.send(message);
                logger.info("发布订阅消息生产者发送消息：{}",msg);
                Thread.sleep(1000);
                session.commit();
            }
            //关闭连接
            activemqUtil.sessionClose(session);
            activemqUtil.connectionClose(connection);
        } catch (JMSException e) {
            logger.error("JMSException:",e);
        } catch (InterruptedException e) {
            logger.error("InterruptedException:", e);
        }
    }

    /**
     * 产生数据
     * @return
     */
    private List<String> buildData(){
        List<String> list = Lists.newArrayList();
        for(int i=0;i<10;i++){
            User user = new User();
            user.setName("0000"+i);
            user.setAge(20 + i);
            user.setEmail("000000" + i + "@test.com");
            list.add(gson.toJson(user));
        }
        return list;
    }
}
