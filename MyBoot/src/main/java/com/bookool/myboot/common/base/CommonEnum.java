package com.bookool.myboot.common.base;

/**
 * 通用枚举接口
 *
 * @param <T> 代码类型
 * @author Tommy
 */
public interface CommonEnum<T extends Number> {

    /**
     * 取得枚举代码
     *
     * @return 枚举代码
     */
    T code();

    /**
     * 取得枚举信息
     *
     * @return 枚举信息
     */
    String message();

}
