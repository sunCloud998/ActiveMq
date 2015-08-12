package com.activemq.study.queue.producer;

import com.activemq.study.domain.User;
import com.activemq.study.util.ActivemqUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.List;

/**
 * 点对点消息生产者
 * @Description: ProducerQueue.java
 * @Author: sunfayun
 * @Date: 2015/08/12
 * @Time: 下午2:49
 * @Version 1.0
 */
public class ProducerQueue {

    private static final Logger logger = LoggerFactory.getLogger(ProducerQueue.class);
    private ActivemqUtil activemqUtil = ActivemqUtil.getInstance();
    private static final String subject = "test.queue";
    private Gson gson = new Gson();

    public static void main(String[] args) {
        ProducerQueue producerQueue = new ProducerQueue();
        producerQueue.sendMessage();
    }

    /**
     * 向MQ发送消息
     * 1.初始化连接工厂ConnectionFactory
     * 2.创建连接Connection
     * 3. 创建会话session
     * 4.打开队列createQueue
     * 5.获得消息生产者MessageProducer
     * 6.使用消息生产者发送消息
     * 7. 关闭会话session和连接Connection
     */
    public void sendMessage(){
        try {
            //初始化连接工厂，并创建连接
            Connection connection = activemqUtil.createConnection();
            //打开连接
            connection.start();
            //创建会话
            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //创建队列
            Destination destination = session.createQueue(subject);
            //创建消息生产者
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();
            List<String> data = this.buildData();
            for(String msg : data){
                message.setText(msg);
                //发送消息
                producer.send(message);
                logger.info("点对点消息生产者发送消息：{}",msg);
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
