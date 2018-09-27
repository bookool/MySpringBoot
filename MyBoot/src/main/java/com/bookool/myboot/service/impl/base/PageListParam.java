package com.bookool.myboot.service.impl.base;

import com.bookool.myboot.common.base.pageresult.BasePageList;
import com.bookool.myboot.common.base.pageresult.PageListException;
import com.bookool.myboot.domain.dto.BaseParam;
import org.jetbrains.annotations.NotNull;

/**
 * 包装分页请求的条件对象，根据条件对象中的 pageNum 和 pageSize 设置MySQL语句中的 offset 和 rows
 *
 * @param <T> 条件对象类型
 * @author Tommy
 */
public class PageListParam<T extends BaseParam> {

    /**
     * 条件对象
     */
    private T param;

    /**
     * MySQL查询中的offset
     */
    private Integer offset;

    /**
     * MySQL查询中的rows
     */
    private Integer rows;

    /**
     * 获取条件对象
     */
    public T getParam() {
        return param;
    }

    /**
     * 获取MySQL查询中的offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * 获取MySQL查询中的rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * 包装分页请求的条件对象，根据条件对象中的 pageNum 和 pageSize 设置MySQL语句中的 offset 和 rows
     *
     * @param param 请求的条件对象
     */
    public PageListParam(@NotNull T param) {
        this.param = param;
        if (param.getPageNum() == null || param.getPageSize() == null) {
            offset = null;
            rows = null;
        } else {
            int pn = param.getPageNum();
            if (pn > BasePageList.PAGE_NUM_MAX || pn < BasePageList.PAGE_NUM_MIN) {
                throw new PageListException(
                        "The requested page number %d, the limit has been exceeded. " +
                                "The range is between %d and %d.",
                        pn,
                        BasePageList.PAGE_NUM_MIN,
                        BasePageList.PAGE_NUM_MAX);
            }
            int ps = param.getPageSize();
            if (ps > BasePageList.PAGE_SIZE_MAX || ps < BasePageList.PAGE_SIZE_MIN) {
                throw new PageListException(
                        "Each page request returns %d records, exceeding the limit. " +
                                "The range is between %d and %d.",
                        ps,
                        BasePageList.PAGE_SIZE_MIN,
                        BasePageList.PAGE_SIZE_MAX);
            }
            offset = (pn - 1) * ps;
            rows = ps;
        }
    }
}
