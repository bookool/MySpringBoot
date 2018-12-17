package com.bookool.myboot.consumer.kafka;

import com.bookool.myboot.common.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * kafka的接收消息测试
 *
 * @author Tommy
 */
@Component
public class UserKafka {

    @KafkaListener(topics = KafkaConfig.TOPIC_TEST)
    public void listen (ConsumerRecord<?, ?> record) {
        System.out.printf("Kafka Receiver 收到  : topic = %s, offset = %d, value = %s, | Time = %s \n",
                record.topic(), record.offset(), record.value(), new Date());
    }

}
