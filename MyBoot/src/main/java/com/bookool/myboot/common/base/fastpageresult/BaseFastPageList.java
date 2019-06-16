package com.bookool.myboot.common.base.fastpageresult;

import java.util.List;
/*
    对于快速更新的数据列表，用户在翻页的时候，很可能就会有新数据插入了
    此时再使用传统分页，就有可能造成新数据插入时，翻页定位上移，发送重复数据
    办法就是使用id进行定位，注意id必须按时间增长
    记录用户观看列表的最大id和最小id，每次请求的时候，先看看有没有比用户观看最大id还大的id，
    即新插入的数据，如果有，要先取出新插入的数据；
    新插入的数据量比请求的单页数少，则再查比用户观看最小id小的数据。
    详细过程参见 FastPageListImpl
 */

/**
 * 主要用于信息更新较快的场景
 * 实现了 FastPageList 接口
 * 详情见 FastPageList 接口
 *
 * @param <R> 结果类型
 * @author Tommy
 */
public abstract class BaseFastPageList<R> implements FastPageList<R> {

    /**
     * 每页请求数量的最小值
     */
    public static final int PAGE_SIZE_MIN = 1;

    /**
     * 每页请求数量的最大值
     */
    public static final int PAGE_SIZE_MAX = 1000;

    /**
     * 请求的本轮请求的最小Id
     */
    @Override
    public Long getRequestRoundMinId() {
        return requestRoundMinId;
    }

    /**
     * 请求的本轮请求的最大Id
     */
    @Override
    public Long getRequestRoundMaxId() {
        return requestRoundMaxId;
    }

    /**
     * 请求的请求的每页数量
     */
    @Override
    public Integer getRequestPageSize() {
        return requestPageSize;
    }

    /**
     * 分页数据
     */
    @Override
    public List<R> getList() {
        return list;
    }

    /**
     * 本轮请求的最小Id
     */
    @Override
    public Long getRoundMinId() {
        return roundMinId;
    }

    /**
     * 本轮请求的最大Id
     */
    @Override
    public Long getRoundMaxId() {
        return roundMaxId;
    }

    /**
     * 返回的记录数
     */
    @Override
    public Integer getCount() {
        return count;
    }

    /**
     * 本轮请求的最小Id
     */
    protected Long requestRoundMinId;

    /**
     * 本轮请求的最大Id
     */
    protected Long requestRoundMaxId;

    /**
     * 请求的每页数量
     */
    protected Integer requestPageSize;

    /**
     * 分页数据
     */
    protected List<R> list;

    /**
     * 本轮请求的最小Id
     */
    protected Long roundMinId;

    /**
     * 本轮请求的最大Id
     */
    protected Long roundMaxId;

    /**
     * 返回的记录数
     */
    protected Integer count;

}
