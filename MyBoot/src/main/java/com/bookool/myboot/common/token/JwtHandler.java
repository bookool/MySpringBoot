package com.bookool.myboot.common.token;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt处理 token 的通用方法
 *
 * @author Tommy
 */
public class JwtHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtHandler.class);

    /**
     * 内容
     */
    public static final String KEY_CON = "con";

    /**
     * jwt签发者
     */
    public static final String KEY_ISS = "iss";

    /**
     * jwt所面向的用户
     */
    public static final String KEY_SUB = "sub";

    /**
     * 接收jwt的一方
     */
    public static final String KEY_AUD = "aud";

    /**
     * jwt的过期时间，这个过期时间必须要大于签发时间
     */
    public static final String KEY_EXP = "exp";

    /**
     * 定义在什么时间之前，该jwt都是不可用的
     */
    public static final String KEY_NBF = "nbf";

    /**
     * jwt的签发时间
     */
    public static final String KEY_IAT = "iat";

    /**
     * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     */
    public static final String KEY_JTI = "jti";


    /**
     * 初始化head部分的数据为 { "alg":"HS256", "type":"JWT" }
     */
    private static final JWSHeader JWS_HEADER = new JWSHeader(JWSAlgorithm.HS256,
            JOSEObjectType.JWT, null, null, null, null, null, null, null, null,
            null, null, null);

    /**
     * 生成JWT
     *
     * @param contentObj   要包装的内容
     * @param expireMillis 过期的毫秒数
     * @param secret       密钥
     * @return JWT字符串, 若失败则返回null
     */
    @NotNull
    public static String createToken(@NotNull Object contentObj, long expireMillis, @NotNull String secret) {
        long iat = System.currentTimeMillis();
        Map<String, Object> jm = new HashMap<>(3);
        jm.put(KEY_CON, contentObj);
        jm.put(KEY_IAT, iat);
        jm.put(KEY_EXP, iat + expireMillis);
        // 创建一个 JWS object
        JWSObject jwsObject = new JWSObject(JWS_HEADER,
                new Payload(new JSONObject(jm)));
        // 将jwsObject 进行HMAC签名
        try {
            MACSigner macsigner = new MACSigner(secret);
            jwsObject.sign(macsigner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Create token fail.");
        }
        return jwsObject.serialize();
    }

    /**
     * 校验签名以及过期时间，取出JWT内容
     *
     * @param jwtString Jwt的字符串
     * @param secret    密钥
     * @return JWT内容，包括创建时间过期时间等，如果出错返回null
     */
    @Nullable
    public static Map<String, Object> getContent(@NotNull String jwtString, @NotNull String secret) {
        Map<String, Object> reval = null;
        try {
            JWSObject jwsObject = JWSObject.parse(jwtString);
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(secret);
            if (jwsObject.verify(verifier)) {
                reval = payload.toJSONObject();
                // 若payload包含ext字段，则校验是否过期
                if (reval.containsKey(KEY_EXP)) {
                    long extTime = (long) reval.get(KEY_EXP);
                    long curTime = System.currentTimeMillis();
                    // 过期了
                    if (curTime > extTime) {
                        reval = null;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return reval;
    }

}
