package com.bookool.myboot.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * WebMvc配置，跨域支持
 * 输出 json ，将所有的 long 变成 string ，因为 javascript 中得数字类型不能包含所有的 java long 值
 *
 * @author Tommy
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 跨域支持
     *
     * @author Tommy
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
        list.add("*");
        corsConfiguration.setAllowedOrigins(list);
        // 请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    /**
     * 序列换成json时,将所有的long变成string,因为 javascript 中得数字类型不能包含所有的java long值
     * 如果客户端使用javascript，则必须如此处理
     * 因为javascript中，所有数据，也就是Number类型统一按浮点数处理，64位存储，
     * 整数是按最大54位来算最大最小数的，否则会丧失精度；某些操作（如数组索引还有位操作）是按32位处理的
     * 整数的范围：−9007199254740992  and 9007199254740992 （即正负2的53次方）
     * 所以要把java的long等类型转为字符串
     * 建议javascript在处理数据时引入Long.js
     *
     * @author Tommy
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper objectMapper = new ObjectMapper();
                // 序列换成json时,将所有的long变成string,因为js中得数字类型不能包含所有的java long值
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
                simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
                simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
                simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
                objectMapper.registerModule(simpleModule);
                MappingJackson2HttpMessageConverter mmc = (MappingJackson2HttpMessageConverter) converter;
                mmc.setObjectMapper(objectMapper);
            }
        }
    }

}
