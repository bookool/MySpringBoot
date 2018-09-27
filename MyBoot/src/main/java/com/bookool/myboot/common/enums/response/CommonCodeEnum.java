package com.bookool.myboot.common.enums.response;

import com.bookool.myboot.common.base.CommonEnum;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务执行结果枚举
 *
 * @author Tommy
 */
public enum CommonCodeEnum implements CommonEnum<Integer> {

    /**
     * 操作失败
     */
    FAIL(-1, "操作失败"),

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

    CommonCodeEnum(int code, String message) {
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

    private static final Map<Integer, String> LOOKUP_MAP = new HashMap<>();

    static {
        for (CommonCodeEnum e : CommonCodeEnum.values()) {
            LOOKUP_MAP.put(e.code, e.message);
        }
    }

    /**
     * 通过枚举代码取得枚举信息
     *
     * @param code 枚举代码
     * @return 枚举信息
     */
    public static String getMessateByCode(int code) {
        return LOOKUP_MAP.get(code);
    }


}
