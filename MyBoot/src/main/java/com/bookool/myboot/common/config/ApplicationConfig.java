package com.bookool.myboot.common.config;

import com.bookool.myboot.common.base.JwtHandler;
import com.bookool.myboot.common.base.SnowflakeIdHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 本应用自动配置类
 *
 * @author Tommy
 */
@Component
@ConfigurationProperties(prefix = "myboot")
public class ApplicationConfig {
    public ApplicationConfig() {
    }

    /**
     * JWT 设置
     */
    private Jwt jwt = new Jwt();

    /**
     * JWT 设置
     */
    public Jwt getJwt() {
        return this.jwt;
    }

    /**
     * JWT 设置
     */
    public static class Jwt {
        Jwt() {
        }

        /**
         * jwt密钥
         */
        private String secret;

        /**
         * jwt过期时间
         */
        private int expire;

        /**
         * jwt重新颁发时间
         */
        private int renewal;

        /**
         * jwt密钥
         */
        public String getSecret() {
            return secret;
        }

        /**
         * jwt密钥
         */
        public void setSecret(String secret) {
            JwtHandler.setSecret(secret);
            this.secret = secret;
        }

        /**
         * jwt过期时间
         */
        public int getExpire() {
            return expire;
        }

        /**
         * jwt过期时间
         */
        public void setExpire(int expire) {
            JwtHandler.setExpire(expire);
            this.expire = expire;
        }

        /**
         * jwt重新颁发时间
         */
        public int getRenewal() {
            return renewal;
        }

        /**
         * jwt重新颁发时间
         */
        public void setRenewal(int renewal) {
            JwtHandler.setRenewal(renewal);
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
