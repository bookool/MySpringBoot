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
 * 如分页效率低下，必须改用 PageListImplByCustom 以提高效率
 * 使用此方法，必须先实现一个获取列表的方法
 * 具体使用可搜索 getListPageByPageHelper 方法，很简单
 *
 * @param <R> 返回结果类型
 * @author Tommy
 */
public class PageListImplByPageHelper<T extends BaseParam, R extends BaseResult> extends BasePageList<T, R> {

    /**
     * 通过 pagehelper 创建分页列表对象，使用时通过此方法创建一个 PageList 接口对象返回
     *
     * @param param        条件对象
     * @param listFunction 获取符合条件的记录列表的方法
     */
    public PageListImplByPageHelper(T param, Function<T, List<R>> listFunction) {
        Preconditions.checkNotNull(param);
        Preconditions.checkNotNull(param.getPageParam().getPageNum());
        Preconditions.checkNotNull(param.getPageParam().getPageSize());
        Preconditions.checkNotNull(listFunction);
        // 初始化参数
        super.checkParam(param);
        super.requestPageNum = param.getPageParam().getPageNum();
        super.requestPageSize = param.getPageParam().getPageSize();
        // 准备使用PageHelper分页
        Page<R> page = PageHelper.startPage(super.requestPageNum, super.requestPageSize);
        // 执行获取列表，PageHelper会自动拦截分页
        listFunction.apply(param);
        // 获取分页完成的对象列表
        PageInfo<R> pageinfo = new PageInfo<>(page);
        super.list = pageinfo.getList();
        super.total = pageinfo.getTotal();
        super.pageNum = pageinfo.getPageNum();
        super.pages = (int) Math.ceil((double) this.total / requestPageSize);
    }

}
