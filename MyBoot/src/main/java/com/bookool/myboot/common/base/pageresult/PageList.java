package com.bookool.myboot.common.base.pageresult;

import java.util.List;

/**
 * 本接口封装了返回分页数据用到的基本信息
 * 本项目的所有分页数据，都必须由此接口格式输出返回数据
 * 具体做法是先由 BasePageList 实现此接口
 * 再由具体的实现类 (在本项目里是 com.bookool.myboot.service.impl.base.PageListByCustom
 * 和 com.bookool.myboot.service.impl.base.PageListByPageHelper)来实现分页方法
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
