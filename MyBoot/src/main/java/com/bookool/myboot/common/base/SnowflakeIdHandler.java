package com.bookool.myboot.common.base;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Twitter_Snowflake <br />
 * 全局id生产 <br />
 * SnowFlake的结构如下(每部分用-分开): <br />
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br />
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0 <br />
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)得到的值，
 * 这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。 <br />
 * 41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69 <br />
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId <br />
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号加起来刚好64位，为一个Long型。 <br />
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，
 * 并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。 <br />
 *
 * @author Tommy
 */
public class SnowflakeIdHandler {

    /**
     * 开始时间截 (2018-01-01 00:00:00)
     */
    private static final long TWEPOCH = 1514736000000L;

    /**
     * 机器id所占的位数
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * 数据中心标识id所占的位数
     */
    private static final long DATA_CENTER_ID_BITS = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * 支持的最大数据中心标识id，结果是31
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    /**
     * 序列在id中占的位数
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 机器ID向左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private static final long TIME_STAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 工作机器ID(0~31)
     */
    private static long workerId = -1L;

    /**
     * 通过配置设置workerid
     *
     * @param workerid 工作机器id
     */
    public static void setWorkerId(long workerid) {
        if (workerid > MAX_WORKER_ID || workerid < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        workerId = workerid;
    }

    /**
     * 数据中心ID(0~31)
     */
    private static long datacenterId = -1L;

    /**
     * 通过配置设置datacenterid
     *
     * @param datacenterid 数据中心ID
     */
    public static void setDatacenterId(long datacenterid) {
        if (datacenterid > MAX_DATA_CENTER_ID || datacenterid < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        datacenterId = datacenterid;
    }

    /**
     * 毫秒内序列(0~4095)
     */
    private static long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private static long lastTimestamp = -1L;

    /**
     * 同步锁
     */
    private static final Object LOCK_OBJECT = new Object();

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    @NotNull
    public static Long nextId() {
        //workerId和datacenterId不正常应当抛出异常
        if (workerId < 0 || workerId > MAX_WORKER_ID || datacenterId < 0 || datacenterId > MAX_DATA_CENTER_ID) {
            throw new RuntimeException("worker id and datacenter id is not set.");
        }

        long timestamp = System.currentTimeMillis();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds.",
                            lastTimestamp - timestamp));
        }

        synchronized (LOCK_OBJECT) {

            //如果是同一时间生成的，则进行毫秒内序列
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & SEQUENCE_MASK;
                //毫秒内序列溢出
                if (sequence == 0) {
                    //阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            }
            //时间戳改变，毫秒内序列重置
            else {
                sequence = 0L;
            }

            //上次生成ID的时间截
            lastTimestamp = timestamp;

        }

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - TWEPOCH) << TIME_STAMP_LEFT_SHIFT)
                | (datacenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
