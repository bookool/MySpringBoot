package com.bookool.myboot.common.enums.response.base;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的业务执行结果枚举
 *
 * @author Tommy
 */
public enum CommonResponseEnum implements ResponseEnum {

    /**
     * 操作失败
     */
    FAIL(-1, "操作失败"),

    /**
     * 还没有登录
     */
    NOT_LOGIN(-2, "还没有登录"),

    /**
     * 添加或更新的信息有重复
     */
    DATA_DUPLICATE(-101, "添加或更新的信息有重复"),

    /**
     * 自定义信息
     */
    CUSTOM(0, ""),

    /**
     * 操作成功
     */
    SUCCESS(1, "操作成功");

    private final int code;

    private final String message;

    CommonResponseEnum(int code, String message) {
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

    private static final Map<Integer, CommonResponseEnum> LOOKUP_MAP = new HashMap<>();

    static {
        for (CommonResponseEnum e : CommonResponseEnum.values()) {
            LOOKUP_MAP.put(e.code, e);
        }
    }

    /**
     * 通过枚举代码取得枚举
     *
     * @param code 枚举代码
     * @return 枚举
     */
    public static CommonResponseEnum getEnumByCode(Integer code) {
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