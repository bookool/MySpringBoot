package com.bookool.myboot.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 实体类基类
 * 实体类必须与数据库表结构完全对应
 * 实体类必须继承此类
 * 阿里 java 规范中规定每个数据表中必须包含三个字段： id 、 gmt_create 、 gmt_modified ，在此类中体现
 * 实体类不暴露给接口，使用时用 CachedBeanCopier 拷贝 dto 对象
 *
 * @author Tommy
 */
@Data
public abstract class BaseEntity {

    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;


}
