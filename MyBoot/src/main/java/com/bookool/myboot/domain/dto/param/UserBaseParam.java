package com.bookool.myboot.domain.dto.param;

import com.bookool.myboot.domain.dto.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * user_base 参数传输对象
 *
 * @author Tommy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserBaseParam extends BaseParam {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户名
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
