package com.bookool.myboot.common.base;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt处理
 * @author Tommy
 */
public class JwtHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtHandler.class);

    /**
     * 一天毫秒数
     */
    private static final long DAY_MILLIS = 24 * 60 * 60 * 1000;

    /**
     * 初始化head部分的数据为 { "alg":"HS256", "type":"JWT" }
     */
    private static final JWSHeader JWS_HEADER = new JWSHeader(JWSAlgorithm.HS256,
            JOSEObjectType.JWT, null, null, null, null, null, null, null, null,
            null, null, null);

    /**
     * jwt密钥
     */
    private static String secret;
    public static void setSecret(String parmSecret) {
        secret = parmSecret;
    }

    /**
     * jwt过期时间
     */
    private static int expire;
    public static void setExpire(int parmExpire) {
        expire = parmExpire;
    }

    /**
     * jwt重新颁发时间
     */
    private static int renewal;
    public static void setRenewal(int parmRenewal) {
        renewal = parmRenewal;
    }

    /**
     * 生成JWT
     * @param contentObj 要包装的内容
     * @param expireMillis 过期的毫秒数
     * @return JWT字符串,若失败则返回null
     */
    public static String createToken(Object contentObj, long expireMillis) {
        long iat = System.currentTimeMillis();
        Map<String, Object> jm = new HashMap<>(3);
        jm.put("con", contentObj);
        jm.put("iat", iat);
        jm.put("exp", iat + expireMillis);
        String tokenString = null;
        try {
            // 创建一个 JWS object
            JWSObject jwsObject = new JWSObject(JWS_HEADER,
                    new Payload(new JSONObject(jm)));
            // 将jwsObject 进行HMAC签名
            jwsObject.sign(new MACSigner(secret));
            tokenString = jwsObject.serialize();
        } catch (JOSEException e) {
            LOGGER.error("签名失败: " + e.getMessage());
            e.printStackTrace();
        }
        return tokenString;
    }

    /**
     * 生成JWT,过期时间为默认配置值
     * @param contentObj 要包装的内容
     * @return JWT字符串,若失败则返回null
     */
    public static String createToken(Object contentObj) {
        return createToken(contentObj, expire * DAY_MILLIS);
    }

}
