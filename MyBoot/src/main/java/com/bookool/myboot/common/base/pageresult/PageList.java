package com.bookool.myboot.common.base.pageresult;

import java.util.List;

/**
 * 分页数据列表
 *
 * @param <R> 结果类型
 * @author Tommy
 */
public interface PageList<R> {

    /**
     * 请求的页码
     *
     * @return 请求的页码
     */
    Integer getRequestPageNum();

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
     * 符合条件的记录总数
     *
     * @return 符合条件的记录总数
     */
    Long getTotal();

    /**
     * 当前的页码
     *
     * @return 当前的页码
     */
    Integer getPageNum();

    /**
     * 总页数
     *
     * @return 总页数
     */
    Integer getPages();

}
