package com.bookool.myboot.common.base;

/**
 * 通用枚举接口
 * 一般枚举都要实现此接口，实现取得枚举代码和信息的方法
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
    T getCode();

    /**
     * 取得枚举信息
     *
     * @return 枚举信息
     */
    String getMessage();

}
