package com.bookool.myboot.common.base.fastpageresult;

import java.util.List;

/**
 * 本接口封装了返回信息更新较快的分页数据用到的基本信息
 * 本项目的所有信息更新较快的分页数据，都必须由此接口格式输出返回数据
 * 具体做法是先由 BaseFastPageList 实现此接口
 *
 * @param <R> 结果类型
 * @author Tommy
 */
public interface FastPageList<R> {

    /**
     * 请求的本轮请求的最小Id
     *
     * @return 本轮请求的最小Id
     */
    Long getRequestRoundMinId();

    /**
     * 请求的本轮请求的最大Id
     */
    Long getRequestRoundMaxId();

    /**
     * 请求的每页数量
     *
     * @return 请求的每页数量
     */
    Integer getRequestPageSize();

    /**
     * 分页数据
     *
     * @return 分页数据
     */
    List<R> getList();

    /**
     * 本轮请求的最小Id
     *
     * @return 本轮请求的最小Id
     */
    Long getRoundMinId();

    /**
     * 本轮请求的最大Id
     *
     * @return 本轮请求的最大Id
     */
    Long getRoundMaxId();

    /**
     * 返回的记录数
     *
     * @return 返回的记录数
     */
    Integer getCount();

}
