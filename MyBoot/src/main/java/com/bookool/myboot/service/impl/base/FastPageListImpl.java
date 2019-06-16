package com.bookool.myboot.service.impl.base;

import com.bookool.myboot.common.base.fastpageresult.BaseFastPageList;
import com.bookool.myboot.common.base.pageresult.BasePageList;
import com.bookool.myboot.common.base.pageresult.PageListException;
import com.bookool.myboot.domain.dto.BaseParam;
import com.bookool.myboot.domain.dto.BaseResult;
import com.google.common.base.Preconditions;

import java.util.List;
import java.util.function.Function;

/**
 * 通过手写SQL语句，处理一个快速更新数据的分页数据请求，通过FastPageList接口返回分页数据
 * 使用此方法，必须在 mapper 中手写一个获取列表的方法
 *
 * @param <T> 请求条件类型
 * @param <R> 返回结果类型
 * @author Tommy
 */
public class FastPageListImpl<T extends BaseParam, R extends BaseResult> extends BaseFastPageList<R> {

    /**
     * 通过手写SQL语句创建分页列表对象，使用时通过此方法创建一个 PageList 接口对象返回
     *
     * @param param            条件对象
     * @param fastListFunction 获取符合条件的记录列表的方法
     */
    public FastPageListImpl(T param, Function<T, List<R>> fastListFunction) {
        /*
        对于快速更新的数据列表，用户在翻页的时候，很可能就会有新数据插入了
        此时再使用传统分页，就有可能造成新数据插入时，翻页定位上移，发送重复数据
        办法就是使用id进行定位，注意id必须按时间增长
        记录用户观看列表的最大id和最小id，每次请求的时候，先看看有没有比用户观看最大id还大的id，
        即新插入的数据，如果有，要先取出新插入的数据；
        新插入的数据量比请求的单页数少，则再查比用户观看最小id小的数据。
         */
        Preconditions.checkNotNull(param);
        Preconditions.checkNotNull(fastListFunction);
        Preconditions.checkNotNull(param.getPageParam());
        Preconditions.checkNotNull(param.getPageParam().getPageSize());
        int ps = param.getPageParam().getPageSize();
        if (ps < BaseFastPageList.PAGE_SIZE_MIN ||
                ps > BaseFastPageList.PAGE_SIZE_MAX) {
            throw new PageListException(
                    "Each page request returns %d records, exceeding the limit. " +
                            "The range is between %d and %d.",
                    ps,
                    BasePageList.PAGE_SIZE_MIN,
                    BasePageList.PAGE_SIZE_MAX);
        }
        super.requestRoundMinId = param.getPageParam().getRoundMinId();
        super.requestRoundMaxId = param.getPageParam().getRoundMaxId();
        super.requestPageSize = param.getPageParam().getPageSize();
        Long minid = param.getPageParam().getRoundMinId();
        Long maxid = param.getPageParam().getRoundMaxId();
        int psize = param.getPageParam().getPageSize();
        // 如果 roundMinId 和 roundMaxId 同时为空表示第一次请求
        if (minid == null && maxid == null) {
            param.getPageParam().setRoundMinId(Long.MAX_VALUE);
            super.list = fastListFunction.apply(param);
        } else if (minid == null || maxid == null) {
            // 如果 roundMinId 和 roundMaxId 有一个为空，则报错
            throw new PageListException( "roundMinId and roundMaxId cannot be null alone.");
        } else {
            param.getPageParam().setRoundMinId(null);
            super.list = fastListFunction.apply(param);
            psize = psize - super.list.size();
            if (psize > 0) {
                param.getPageParam().setPageSize(psize);
                param.getPageParam().setRoundMaxId(null);
                param.getPageParam().setRoundMinId(minid);
                List<R> slist = fastListFunction.apply(param);
                super.list.addAll(slist);
            }
        }
        minid = super.requestRoundMinId;
        maxid = super.requestRoundMaxId;
        for (R li : super.list) {
            long liid = li.getId();
            if (minid == null) {
                minid = liid;
            } else if (minid > liid) {
                minid = liid;
            }
            if (maxid == null) {
                maxid = liid;
            } else if (maxid < liid) {
                maxid = liid;
            }
        }
        super.roundMinId = minid;
        super.roundMaxId = maxid;
        super.count = super.list.size();
    }

}
