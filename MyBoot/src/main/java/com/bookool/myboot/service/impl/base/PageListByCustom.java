package com.bookool.myboot.service.impl.base;

import com.bookool.myboot.common.base.pageresult.BasePageList;
import com.bookool.myboot.common.base.pageresult.PageListException;
import com.bookool.myboot.domain.dto.BaseParam;
import com.bookool.myboot.domain.dto.BaseResult;
import com.google.common.base.Preconditions;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 请求分页列表的处理
 *
 * @param <T> 请求条件类型
 * @param <R> 返回结果类型
 * @author Tommy
 */
public class PageListByCustom<T extends BaseParam, R extends BaseResult> extends BasePageList<R> {

    /**
     * 创建分页列表对象
     *
     * @param param         条件对象
     * @param countFunction 获取符合条件的记录的个数的方法
     * @param listFunction  获取符合条件的记录列表
     */
    public PageListByCustom(T param,
                            Function<T, Long> countFunction,
                            Function<PageListParam<T>, List<R>> listFunction) {
        Preconditions.checkNotNull(param);
        Preconditions.checkNotNull(countFunction);
        Preconditions.checkNotNull(listFunction);
        // 请求条件的参数对象
        PageListParam<T> pageListParam = new PageListParam<>(param);
        super.requestPageNum = pageListParam.getParam().getPageNum();
        super.requestPageSize = pageListParam.getParam().getPageSize();
        // 先取符合条件记录总数
        super.total = countFunction.apply(param);
        // 如果记录总数为0
        if (super.total == 0) {
            super.pages = 0;
            this.setEmptyList();
        } else { // 记录总数大于0
            // 如果有分页请求
            if (super.getRequestPageNum() != null && super.getRequestPageSize() != null) {
                // 计算所有页数
                super.pages = (int) Math.ceil((float) super.total / super.getRequestPageSize());
                // 如果请求页码在所有页数内
                if (super.getRequestPageNum() <= super.pages) {
                    this.setList(pageListParam, listFunction);
                } else { // 如果请求页码不在所有页数内
                    this.setEmptyList();
                }
            } else { // 如果没有分页请求
                // 如果符合条件记录数小于或等于单页请求的最大记录数
                if (super.total <= BasePageList.PAGE_SIZE_MAX) {
                    this.setList(pageListParam, listFunction);
                } else { // 如果符合条件记录数大于单页请求的最大记录数，抛出异常
                    throw new PageListException(
                            "Request to return %d records, exceeding the limit. " +
                                    "You can only return up to %d records.",
                            super.total,
                            BasePageList.PAGE_SIZE_MAX);
                }
            }
        }
    }

    /**
     * 设置结果为空列表
     */
    private void setEmptyList() {
        // 设置当前页码置为0
        super.pageNum = 0;
        // 返回空列表
        super.list = Collections.emptyList();
    }

    /**
     * 设置正常结果列表
     */
    private void setList(PageListParam<T> pageListParam,
                         Function<PageListParam<T>, List<R>> listFunction) {
        // 设置当前页码
        super.pageNum = super.requestPageNum;
        // 取出当页内记录列表
        super.list = listFunction.apply(pageListParam);
    }

}