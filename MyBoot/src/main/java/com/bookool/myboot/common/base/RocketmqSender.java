package com.bookool.myboot.common.base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Date;

import static com.bookool.myboot.common.config.RocketmqConfig.namesrvAddr;

/**
 * Kafka 消息发送器
 *
 * @author Tommy
 */
@Component
public class RocketmqSender {

    public void send(String message, String groupName, String topic, String tag) {
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        try {
            producer.setSendMsgTimeout(100000);
            producer.setVipChannelEnabled(false);
            producer.start();
            Message rmessage = new Message(topic, tag, message.getBytes());
            System.out.println("RocketmqSender发送内容 : " + message + " | " + new Date());
            SendResult result = producer.send(rmessage);
            System.out.println("RocketmqSender发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

}
