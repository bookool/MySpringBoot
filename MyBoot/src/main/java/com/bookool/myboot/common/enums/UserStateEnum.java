package com.bookool.myboot.common.enums;

import com.bookool.myboot.common.base.CommonEnum;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * 此枚举值对应数据库 user 表中的 user_state 字段
 * 数据库中所有状态字段都应建立枚举对应
 *
 * @author Tommy
 */
public enum UserStateEnum implements CommonEnum<Integer> {
    /**
     * 用户无效
     */
    DISABLE(0, "无效"),

    /**
     * 用户有效
     */
    ENABLE(1, "有效");

    private final Integer code;

    private final String message;

    UserStateEnum(Integer code, String message) {
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
    public Integer code() {
        return code;
    }

    /**
     * 取得枚举信息
     *
     * @return 枚举信息
     */
    @Contract(pure = true)
    @Override
    public String message() {
        return message;
    }

    private static final Map<Integer, UserStateEnum> LOOKUP_MAP = new HashMap<>();

    static {
        for (UserStateEnum e : UserStateEnum.values()) {
            LOOKUP_MAP.put(e.code, e);
        }
    }

    /**
     * 通过枚举代码取得枚举
     *
     * @param code 枚举代码
     * @return 枚举
     */
    public static UserStateEnum getEnumByCode(Integer code) {
        return LOOKUP_MAP.get(code);
    }

    /**
     * 通过枚举代码取得枚举信息
     *
     * @param code 枚举代码
     * @return 枚举信息
     */
    public static String getMessateByCode(Integer code) {
        return LOOKUP_MAP.get(code).message;
    }

}
