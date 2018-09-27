package com.bookool.myboot.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 实体类基类
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
