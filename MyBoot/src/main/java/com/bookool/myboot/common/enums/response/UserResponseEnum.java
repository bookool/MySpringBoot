package com.bookool.myboot.common.enums.response;

import com.bookool.myboot.common.enums.response.base.ResponseEnum;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * 和用户相关的业务执行结果枚举
 * code 的范围参看项目内《业务结果代码范围控制》
 *
 * @author Tommy
 */
public enum UserResponseEnum implements ResponseEnum {

    /**
     * 用户密码错误
     */
    PASSWORD_ERROR(-1003, "用户密码错误"),

    /**
     * 用户已经被禁用
     */
    USER_DISABLE(-1002, "用户已经被禁用"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(-1001, "用户不存在"),

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(1001, "登录成功");

    private final int code;

    private final String message;

    UserResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 取得枚举代码
     *
     * @return 枚举代码
     */
    @Contract(pure = true)
    @Override
    public Integer getCode() {
        return code;
    }

    /**
     * 取得枚举信息
     *
     * @return 枚举信息
     */
    @Contract(pure = true)
    @Override
    public String getMessage() {
        return message;
    }

    private static final Map<Integer, UserResponseEnum> LOOKUP_MAP = new HashMap<>();

    static {
        for (UserResponseEnum e : UserResponseEnum.values()) {
            LOOKUP_MAP.put(e.getCode(), e);
        }
    }

    /**
     * 通过枚举代码取得枚举
     *
     * @param code 枚举代码
     * @return 枚举
     */
    public static UserResponseEnum getEnumByCode(Integer code) {
        return LOOKUP_MAP.get(code);
    }

    /**
     * 通过枚举代码取得枚举信息
     *
     * @param code 枚举代码
     * @return 枚举信息
     */
    public static String getMessateByCode(Integer code) {
        return LOOKUP_MAP.get(code).getMessage();
    }

}