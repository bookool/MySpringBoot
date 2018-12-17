package com.bookool.myboot.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Rocketmq的配置信息
 * @author Tommy
 */
@Component
@ConfigurationProperties("myboot.rocketmq")
public class RocketmqConfig {

    /**
     * namesrvAddr地址
     */
    public static String namesrvAddr;

    /**
     * namesrvAddr地址
     */
    public void setNamesrvAddr(String namesrvAddr) {
        RocketmqConfig.namesrvAddr = namesrvAddr;
    }

    public static final String GROUP_NAME = "myboot-group";

    public static final String TOPIC_USER = "user";

    public static final String TAG_USER = "userinfo";

}