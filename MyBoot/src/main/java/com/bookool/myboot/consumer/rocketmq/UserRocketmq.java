package com.bookool.myboot.consumer.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Date;

import static com.bookool.myboot.common.config.RocketmqConfig.*;

/**
 * Rocketmq 接收消息测试
 *
 * @author Tommy
 */
@Component
public class UserRocketmq {

    private Logger log = LoggerFactory.getLogger(UserRocketmq.class);

    @PostConstruct
    public void consumer() {
        log.info("Rocketmq init defaultMQPushConsumer");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP_NAME);
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            consumer.subscribe(TOPIC_USER, TAG_USER);
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                try {
                    for (MessageExt messageExt : list) {
                        //输出消息内容
                        System.out.println("Rocketmq 收到  : " + new String(messageExt.getBody()) + " | " + new Date());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //稍后再试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                //消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
