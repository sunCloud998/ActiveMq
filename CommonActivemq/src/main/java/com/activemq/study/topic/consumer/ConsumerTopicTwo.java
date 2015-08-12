package com.activemq.study.topic.consumer;

import com.activemq.study.util.ActivemqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * 发布订阅消息消费者2
 * @Description: ConsumerTopicTwo.java
 * @Author: sunfayun
 * @Date: 2015/08/12
 * @Time: 下午2:52
 * @Version 1.0
 */
public class ConsumerTopicTwo {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerTopicTwo.class);
    private ActivemqUtil activemqUtil = ActivemqUtil.getInstance();
    private static final String subject = "test.topic";

    public static void main(String[] args) {
        ConsumerTopicTwo consumerTopicTwo = new ConsumerTopicTwo();
        consumerTopicTwo.receiveMessage();
    }

    /**
     * 从MQ接受消息
     * 1.初始化连接工厂ConnectionFactory
     * 2.创建连接Connection
     * 3. 创建会话session
     * 4.打开队列createQueue
     * 5.获得消息消费者MessageConsumer
     * 6.使用MessageConsumer接受消息
     * 7. 关闭会话session和连接Connection
     */
    public void receiveMessage(){
        try {
            //初始化连接工厂，并创建连接
            Connection connection = activemqUtil.createConnection();
            //打开连接
            connection.start();
            //创建会话
            final Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //创建要发布的主题，和Queue的区别就在此
            Destination destination = session.createTopic(subject);
            //创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String msg = textMessage.getText();
                        logger.info("发布订阅消费者【2】接收到消息：{}", msg);
                        session.commit();
                        Thread.sleep(2000);
                    } catch (JMSException e) {
                        logger.error("JMSException:", e);
                    } catch (InterruptedException e) {
                        logger.error("InterruptedException:", e);
                    }
                }
            });
        } catch (JMSException e) {
            logger.error("JMSException:", e);
        }
    }

}
