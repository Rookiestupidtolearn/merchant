package com.doubao.merchant.server.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ScheduledMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Send {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(Send.class);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue delayQueue;

    /**
     * 首先要在中间件MQ conf文件下的activeMQ.xml 中 的broker节点中  
     * schedulerSupport="true"  启用延时投递
     * @param str
     * @param time
     */
    public void delaySend(String orderNo,long time){
        //获取连接工厂
    	ConnectionFactory connectionFactory = jmsMessagingTemplate.getConnectionFactory();
    	Connection connection = null;
    	Session session = null;
    	MessageProducer producer = null;
    	try {
    		// 获取连接
            connection = connectionFactory.createConnection();
            connection.start();
            // 获取session，true开启事务，false关闭事务
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            producer = session.createProducer(this.delayQueue);
            producer.setDeliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue());
            TextMessage message = session.createTextMessage(orderNo);
            //设置延迟时间
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
            // 发送消息
            producer.send(message);
            session.commit();
            LOGGER.info("延时队列发送消息成功："+orderNo);
		} catch (Exception e) {
			LOGGER.error("延时队列-发送消息出现异常：",e);
		}finally{
			try {
				if(producer != null){
						producer.close();
				}
				if(session!=null){
					session.close();
				}
				if(connection != null){
					connection.close();
				}
			} catch (JMSException e) {
				LOGGER.error("延时队列-关闭资源异常：",e);;
			}
		}
    }
}
