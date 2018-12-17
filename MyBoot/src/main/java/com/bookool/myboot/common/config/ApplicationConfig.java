package com.bookool.myboot.common.config;

import com.bookool.myboot.common.base.SnowflakeIdHandler;
import com.bookool.myboot.common.token.user.UserTokenHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 本应用自动配置类
 *
 * @author Tommy
 */
@Component
@ConfigurationProperties("myboot")
public class ApplicationConfig {
    public ApplicationConfig() {
    }

    /**
     * UserToken 设置
     */
    private UserToken userToken = new UserToken();

    /**
     * UserToken 设置
     */
    public UserToken getUserToken() {
        return this.userToken;
    }

    /**
     * UserToken 设置
     */
    public static class UserToken {
        UserToken() {
        }

        /**
         * UserToken密钥
         */
        private String secret;

        /**
         * UserToken过期时间
         */
        private int expire;

        /**
         * UserToken重新颁发时间
         */
        private int renewal;

        /**
         * UserToken密钥
         */
        public String getSecret() {
            return secret;
        }

        /**
         * UserToken密钥
         */
        public void setSecret(String secret) {
            UserTokenHandler.setSecret(secret);
            this.secret = secret;
        }

        /**
         * UserToken过期时间
         */
        public int getExpire() {
            return expire;
        }

        /**
         * UserToken过期时间
         */
        public void setExpire(int expire) {
            UserTokenHandler.setExpire(expire);
            this.expire = expire;
        }

        /**
         * UserToken重新颁发时间
         */
        public int getRenewal() {
            return renewal;
        }

        /**
         * UserToken重新颁发时间
         */
        public void setRenewal(int renewal) {
            UserTokenHandler.setRenewal(renewal);
            this.renewal = renewal;
        }

    }

    /**
     * Twitter_Snowflake 全局id生产
     */
    private Snowflake snowflake = new Snowflake();

    /**
     * Twitter_Snowflake 全局id生产
     */
    public Snowflake getSnowflake() {
        return this.snowflake;
    }

    /**
     * Twitter_Snowflake 全局id生产
     */
    public static class Snowflake {
        Snowflake() {
        }

        /**
         * 数据中心id
         */
        private long datacenterid;

        /**
         * 工作机器id
         */
        private long workerid;

        /**
         * 数据中心id
         */
        public long getDatacenterid() {
            return datacenterid;
        }

        /**
         * 数据中心id
         */
        public void setDatacenterid(long datacenterid) {
            SnowflakeIdHandler.setDatacenterId(datacenterid);
            this.datacenterid = datacenterid;
        }

        /**
         * 工作机器id
         */
        public long getWorkerid() {
            return workerid;
        }

        /**
         * 工作机器id
         */
        public void setWorkerid(long workerid) {
            SnowflakeIdHandler.setWorkerId(workerid);
            this.workerid = workerid;
        }

    }

}
