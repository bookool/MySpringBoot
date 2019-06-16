package com.bookool.myboot.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * 分页参数对象
 *
 * @author Tommy
 */
@Data
public class PageParam {

    /**
     * MySQL查询中的offset
     */
    private Integer offset;

    /**
     * MySQL查询中的rows
     */
    private Integer rows;

    /**
     * 请求的页码
     */
    private Integer pageNum;

    /**
     * 请求的每页数量
     */
    private Integer pageSize;

    /**
     * 本轮请求的最小Id
     */
    private Long roundMinId;

    /**
     * 本轮请求的最大Id
     */
    private Long roundMaxId;

}
