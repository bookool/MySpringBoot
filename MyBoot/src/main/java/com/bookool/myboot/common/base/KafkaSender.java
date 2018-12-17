package com.bookool.myboot.common.base;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Kafka 消息发送器
 *
 * @author Tommy
 */
@Component
public class KafkaSender {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
        System.out.printf("Kafka Sender 发送内容 : topic = %s, message = %s, | Time = %s \n",
                topic, message, new Date());
    }

}
