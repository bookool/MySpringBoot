package com.bookool.myboot.domain.dto.result;

import com.bookool.myboot.domain.dto.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * user_base 结果传输对象
 *
 * @author Tommy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserBaseResult extends BaseResult {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户名(至少3位,必须包含非数字非符合的字符,不能包含@符)
     */
    private String userName;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 微信OpenID
     */
    private String wxOpenid;

    /**
     * 微信UnionID
     */
    private String wxUnionid;

    /**
     * 用户密码MD5
     */
    private String userPassword;

    /**
     * 用户修改密码的时间
     */
    private Date passwordModified;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像链接地址
     */
    private String iconUrl;

    /**
     * 用户状态
     */
    private Short userState;

    /**
     * 用户等级
     */
    private Short userStatus;

}
