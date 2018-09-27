package com.bookool.myboot.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * 参数类传输对象(dto输入)基类
 *
 * @author Tommy
 */
@Data
public abstract class BaseParam {

    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 请求的页码
     */
    Integer pageNum;

    /**
     * 请求的每页数量
     */
    Integer pageSize;

}
