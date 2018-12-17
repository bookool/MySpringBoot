package com.bookool.myboot.common.token.user;

import com.bookool.myboot.common.token.JwtHandler;
import com.bookool.myboot.service.UserBaseService;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User Token 处理
 * user token 在客户端发来请求的header中
 * 使用 JWT 对 token 进行处理
 * 在 RequestAspect 中调用
 *
 * @author Tommy
 */
@Component
public class UserTokenHandler {

    private static UserBaseService userBaseService;

    /**
     * 通过setter方法注入
     */
    @Resource
    public void setUserBaseService(UserBaseService userBaseService) {
        UserTokenHandler.userBaseService = userBaseService;
    }

    /**
     * user token 在客户端发来请求的header中的名称
     */
    public static final String HEADER_NAME = "USER-TOKEN";

    /**
     * userid 取出后，在 request Attribute 中保存的键值
     */
    public static final String REQUEST_ATTRIBUTE_KEY = "UserId";

    /**
     * Userid 在jwt内容中的键值
     */
    private static final String JWT_CONTENT_KEY_USERID = "userId";

    /**
     * 一天毫秒数
     */
    private static final long DAY_MILLIS = 24 * 60 * 60 * 1000;

    /**
     * UserToken密钥
     */
    private static String secret;

    public static void setSecret(String parmSecret) {
        secret = parmSecret;
    }

    /**
     * UserToken过期时间
     */
    private static int expire;

    public static void setExpire(int parmExpire) {
        expire = parmExpire;
    }

    /**
     * UserToken重新颁发时间
     */
    private static int renewal;

    public static void setRenewal(int parmRenewal) {
        renewal = parmRenewal;
    }

    /**
     * 生成UserToken,过期时间为默认配置值
     *
     * @param userId 用户id
     * @return UserToken字符串, 若失败则返回null
     */
    @NotNull
    public static String createToken(@NotNull Long userId) {
        Map<String, Object> contentObj = new HashMap<>(1);
        contentObj.put(JWT_CONTENT_KEY_USERID, userId);
        return JwtHandler.createToken(contentObj, expire * DAY_MILLIS, secret);
    }

    /**
     * 从Jwt中取得Userid的方法，同时返回是否需要更新jwt
     *
     * @param jwtString jwt字符串
     * @return 如果jwt有效，则返回userid，同时返回是否需要更新jwt
     */
    @Nullable
    public static Pair<Long, Boolean> getUserId(@NotNull String jwtString) {
        Map<String, Object> getvalue = JwtHandler.getContent(jwtString, secret);
        if (getvalue != null) {
            if (getvalue.containsKey(JwtHandler.KEY_CON) && getvalue.containsKey(JwtHandler.KEY_IAT)) {
                try {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> contentObj = (Map<String, Object>) getvalue.get(JwtHandler.KEY_CON);
                    long userId = (long) contentObj.get(JWT_CONTENT_KEY_USERID);
                    long iat = (long) getvalue.get(JwtHandler.KEY_IAT);
                    Date iatDate = new Date(iat);
                    // 检查用户是否有效，只有在有效的情况下返回Pair，其余情况均返回null
                    if (userBaseService.isUserEnable(userId, iatDate)) {
                        long renew = renewal * DAY_MILLIS + iat;
                        return Pair.of(userId, System.currentTimeMillis() > renew);
                    }
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

}
