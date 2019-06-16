package com.bookool.myboot.elasticsearch.config;

/**
 * ES 配置
 *
 * @author Tommy
 */
public class ElasticsearchConfig {

    /**
     * index名称
     */
    public static final String INDEX_NAME = "myboot";

    /**
     * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
     * 解决netty冲突后初始化client时还会抛出异常
     * java.lang.IllegalStateException: availableProcessors is already set to [8], rejecting [8]
     */
    public static void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

}
