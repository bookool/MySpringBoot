package com.bookool.myboot.common.base;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Rabbit 消息发送器
 *
 * @author Tommy
 */
@Component
public class RabbitSender implements RabbitTemplate.ReturnCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String routingKey, String message) {
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("ConfirmCallback:MqSender消息发送失败"
                        + cause + correlationData.toString() + " | " + new Date());
            } else {
                System.out.println("ConfirmCallback:MqSender消息发送成功 " + " | " + new Date());
            }
        });
        System.out.println("RabbitMqSender发送内容 : " + message + " | " + new Date());
        this.rabbitTemplate.convertAndSend(routingKey, message);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("RabbitMqReturnedMessage: " + message.toString()
                + "===" + i + "===" + s1 + "===" + s2 + " | " + new Date());
    }

}
