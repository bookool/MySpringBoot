package com.bookool.myboot.domain.dto.result;

import com.bookool.myboot.domain.dto.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * user 用户基础信息表 结果传输对象
 *
 * @author Tommy
 * @date 2019-05-25 20:50:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserResult extends BaseResult {

    /**
     * 手机号码
     */
    private java.lang.String mobile;

    /**
     * 用户名
     */
    private java.lang.String userName;

    /**
     * 用户密码MD5
     */
    private java.lang.String userPassword;

    /**
     * 用户状态
     */
    private java.lang.Integer userState;

    /**
     * 用户修改密码的时间
     */
    private java.util.Date passwordModified;


}
