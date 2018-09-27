package com.bookool.myboot.common.base;

import com.bookool.myboot.common.enums.response.BussinessEnum;
import com.bookool.myboot.common.enums.response.CommonCodeEnum;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.Map;

/**
 * Controller通用方法
 *
 * @author Tommy
 */
public abstract class BaseController {

    /**
     * 返回操作成功，自定义信息，无数据
     *
     * @param msg 返回信息
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map successJsonMsg(String msg) {
        return getJsonMsg(BussinessEnum.SUCCESS, msg, null);
    }

    /**
     * 返回操作成功，自定义信息，有数据
     *
     * @param msg  返回信息
     * @param data 返回数据
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map successJsonMsg(String msg, Map data) {
        return getJsonMsg(BussinessEnum.SUCCESS, msg, data);
    }

    /**
     * 返回操作成功，默认代码，无数据
     *
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map successJsonMsg() {
        return getJsonMsg(BussinessEnum.SUCCESS, CommonCodeEnum.SUCCESS, null);
    }

    /**
     * 返回操作成功，默认代码，有数据
     *
     * @param data 返回数据
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map successJsonMsg(Map data) {
        return getJsonMsg(BussinessEnum.SUCCESS, CommonCodeEnum.SUCCESS, data);
    }

    /**
     * 返回操作成功，预定义代码，无数据
     *
     * @param codeEnum 预定义代码枚举
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map successJsonMsg(CommonCodeEnum codeEnum) {
        return getJsonMsg(BussinessEnum.SUCCESS, codeEnum, null);
    }

    /**
     * 返回操作成功，预定义代码，有数据
     *
     * @param codeEnum 预定义代码枚举
     * @param data     返回数据
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map successJsonMsg(CommonCodeEnum codeEnum, Map data) {
        return getJsonMsg(BussinessEnum.SUCCESS, codeEnum, data);
    }

    /**
     * 返回操作失败，自定义信息，无数据
     *
     * @param msg 返回信息
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map failJsonMsg(String msg) {
        return getJsonMsg(BussinessEnum.FAIL, msg, null);
    }

    /**
     * 返回操作失败，自定义信息，有数据
     *
     * @param msg  返回信息
     * @param data 返回数据
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map failJsonMsg(String msg, Map data) {
        return getJsonMsg(BussinessEnum.FAIL, msg, data);
    }

    /**
     * 返回操作失败，默认代码，无数据
     *
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map failJsonMsg() {
        return getJsonMsg(BussinessEnum.FAIL, CommonCodeEnum.FAIL, null);
    }

    /**
     * 返回操作失败，默认代码，有数据
     *
     * @param data 返回数据
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map failJsonMsg(Map data) {
        return getJsonMsg(BussinessEnum.FAIL, CommonCodeEnum.FAIL, data);
    }

    /**
     * 返回操作失败，预定义代码，无数据
     *
     * @param codeEnum 预定义代码枚举
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map failJsonMsg(CommonCodeEnum codeEnum) {
        return getJsonMsg(BussinessEnum.FAIL, codeEnum, null);
    }

    /**
     * 返回操作失败，预定义代码，有数据
     *
     * @param codeEnum 预定义代码枚举
     * @param data     返回数据
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map failJsonMsg(CommonCodeEnum codeEnum, Map data) {
        return getJsonMsg(BussinessEnum.FAIL, codeEnum, data);
    }

    // -----------------------------------------------------------------------------

    /**
     * 包装返回数据，自定义信息
     *
     * @param successEnum 业务结果枚举
     * @param msg         返回信息
     * @param data        返回数据
     * @return 返回Json包
     * @author Tommy
     */
    private static Map getJsonMsg(BussinessEnum successEnum, String msg, Map data) {
        return ImmutableMap.of("success", successEnum.code(),
                "code", CommonCodeEnum.CUSTOM.code(),
                "message", msg,
                "data", data == null ? Collections.emptyMap() : data
        );
    }

    /**
     * 包装返回数据，返回预定义代码信息，不返回字符信息
     *
     * @param successEnum 业务结果枚举
     * @param codeEnum    返回业务代码枚举
     * @param data        返回数据
     * @return 返回Json包
     * @author Tommy
     */
    private static Map getJsonMsg(BussinessEnum successEnum, CommonCodeEnum codeEnum, Map data) {
        return ImmutableMap.of("success", successEnum.code(),
                "code", codeEnum.code(),
                "message", codeEnum.message(),
                "data", data == null ? Collections.emptyMap() : data
        );
    }

    /**
     * 包装返回数据，返回预定义代码信息，不返回字符信息
     *
     * @param successEnum 业务结果枚举
     * @param codeEnum    返回业务代码枚举
     * @param data        返回数据
     * @return 返回Json包
     * @author Tommy
     */
    private static Map getJsonMsg(BussinessEnum successEnum, CommonEnum codeEnum, Map data) {
        return ImmutableMap.of("success", successEnum.code(),
                "code", codeEnum.code(),
                "message", codeEnum.message(),
                "data", data == null ? Collections.emptyMap() : data
        );
    }


}
