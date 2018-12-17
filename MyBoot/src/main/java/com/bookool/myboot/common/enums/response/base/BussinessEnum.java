package com.bookool.myboot.common.enums.response.base;

import com.bookool.myboot.common.base.CommonEnum;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务执行结果枚举，对前端的应答中的 success 项
 *
 * @author Tommy
 */
public enum BussinessEnum implements CommonEnum<Integer> {
    /**
     * 业务失败
     */
    FAIL(0, "失败"),

    /**
     * 业务成功
     */
    SUCCESS(1, "成功");

    private final int code;

    private final String message;

    BussinessEnum(int code, String message) {
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
        for (BussinessEnum e : BussinessEnum.values()) {
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
