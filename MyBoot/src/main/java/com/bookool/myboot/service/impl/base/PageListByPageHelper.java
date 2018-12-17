package com.bookool.myboot.service.impl.base;

import com.bookool.myboot.common.base.pageresult.BasePageList;
import com.bookool.myboot.domain.dto.BaseParam;
import com.bookool.myboot.domain.dto.BaseResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;

import java.util.List;
import java.util.function.Function;

/**
 * 通过 pagehelper 处理一个分页数据请求，通过PageList接口返回分页数据
 * 对于记录数不超过百万的数据表，可使用此方法进行分页
 * 如分页效率低下，必须改用 PageListByCustom 以提高效率
 * 使用此方法，必须先实现一个获取列表的方法
 * 具体使用可搜索 getListPageByPageHelper 方法，很简单
 *
 * @param <R> 返回结果类型
 * @author Tommy
 */
public class PageListByPageHelper<T extends BaseParam, R extends BaseResult> extends BasePageList<R> {

    /**
     * 通过 pagehelper 创建分页列表对象，使用时通过此方法创建一个 PageList 接口对象返回
     *
     * @param param        条件对象
     * @param listFunction 获取符合条件的记录列表的方法
     */
    public PageListByPageHelper(T param, Function<PageListParam<T>, List<R>> listFunction) {
        Preconditions.checkNotNull(param);
        Preconditions.checkNotNull(param.getPageNum());
        Preconditions.checkNotNull(param.getPageSize());
        Preconditions.checkNotNull(listFunction);
        // 请求条件的参数对象
        // 此处借用pageListParam，主要用作参数检查，同时在mapper的xml中复用where块
        PageListParam<T> pageListParam = new PageListParam<>(param);
        super.requestPageNum = pageListParam.getParam().getPageNum();
        super.requestPageSize = pageListParam.getParam().getPageSize();
        // 准备使用PageHelper分页
        Page<R> page = PageHelper.startPage(super.requestPageNum, super.requestPageSize);
        // 执行获取列表，PageHelper会自动拦截分页
        listFunction.apply(pageListParam);
        // 获取分页完成的对象列表
        PageInfo<R> pageinfo = new PageInfo<>(page);
        super.list = pageinfo.getList();
        super.total = pageinfo.getTotal();
        super.pageNum = pageinfo.getPageNum();
        super.pages = (int) Math.ceil((double) this.total / requestPageSize);
    }

}
