package com.bookool.myboot.consumer.rabbit;

import com.bookool.myboot.common.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * rabbit 接收消息测试
 *
 * @author Tommy
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_USER)
public class UserRabbit {

    private Logger log = LoggerFactory.getLogger(UserRabbit.class);

    @RabbitHandler
    public void process(String userJson, Channel channel, Message message) {
        System.out.println("MqReceiver收到  : " + userJson + " | " + new Date());
        try {
            // 告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了
            // 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("RabbitMqReceiver success" + " | " + new Date());
        } catch (Exception e) {
            e.printStackTrace();
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            System.out.println("RabbitMqReceiver fail" + " | " + new Date());
        }

    }
}
