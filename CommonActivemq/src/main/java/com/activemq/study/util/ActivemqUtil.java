package com.activemq.study.util;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @Description: ActivemqUtil.java
 * @Author: sunfayun
 * @Date: 2015/08/12
 * @Time: 下午2:22
 * @Version 1.0
 */
public class ActivemqUtil {

    private Logger logger = LoggerFactory.getLogger(ActivemqUtil.class);
    private String userName = ActiveMQConnection.DEFAULT_USER;
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private ConnectionFactory connectionFactory;
    private static ActivemqUtil activemqUtil = new ActivemqUtil();

    private ActivemqUtil(){
        connectionFactory = new ActiveMQConnectionFactory(userName,password,url);
    }

    public static ActivemqUtil getInstance(){
        return activemqUtil;
    }

    /**
     * 创建连接
     * @return
     * @throws JMSException
     */
    public Connection createConnection() throws JMSException {
        return connectionFactory.createConnection();
    }

    /**
     * 关闭Session
     * @param session
     */
    public void sessionClose(Session session){
        if(null == session){
            return;
        }
        try {
            session.close();
        } catch (JMSException e) {
            logger.error("JMSException:",e);
        }
    }

    /**
     * 关闭Connection
     * @param connection
     */
    public void connectionClose(Connection connection){
        if(null == connection){
            return;
        }
        try {
            connection.close();
        } catch (JMSException e) {
            logger.error("JMSException:", e);
        }
    }
}
