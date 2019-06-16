package com.bookool.myboot;

import com.bookool.myboot.elasticsearch.config.ElasticsearchConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Tommy
 * @date 2018-08-05
 */
@SpringBootApplication
@EnableAsync
@MapperScan("com.bookool.myboot.mapper")
public class App {
    public static void main(String[] args) {
        ElasticsearchConfig.init();
        SpringApplication.run(App.class, args);
    }
}
