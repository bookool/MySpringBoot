package com.bookool.myboot.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * 结果类传输对象(dto输出)基类
 *
 * @author Tommy
 */
@Data
public abstract class BaseResult {

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

}
