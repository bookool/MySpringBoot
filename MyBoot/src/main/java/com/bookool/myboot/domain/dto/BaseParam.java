package com.bookool.myboot.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * 参数类传输对象(输入)基类
 * 所有输入参数对象必须继承
 * 阿里 java 规范中规定每个数据表中必须包含三个字段： id 、 gmt_create 、 gmt_modified ，在此类中体现
 * 但编写业务代码时，无需手动赋值，参见 BaseMapper
 * 另外需注意本项目不使用自增长字段， id 的赋值在 BaseMapper 中自动实现
 * pageNum 和 pageSize 在分页时使用
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
