package com.bookool.myboot.common.base;

import lombok.Data;

import java.util.Date;

/**
 * UserBase
 *
 * @author Tommy
 */
@Data
public abstract class BaseModel {

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
