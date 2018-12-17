package com.bookool.myboot.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * 业务结果对象(输出)基类
 * 所有输出业务结果对象必须继承
 * 阿里 java 规范中规定每个数据表中必须包含三个字段： id 、 gmt_create 、 gmt_modified ，在此类中体现
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
