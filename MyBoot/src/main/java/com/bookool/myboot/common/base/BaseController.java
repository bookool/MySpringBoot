package com.bookool.myboot.common.base;

import com.bookool.myboot.common.enums.response.base.BussinessEnum;
import com.bookool.myboot.common.enums.response.base.CommonResponseEnum;
import com.bookool.myboot.common.enums.response.base.ResponseEnum;
import com.bookool.myboot.common.token.user.UserTokenHandler;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

/**
 * Controller 通用方法
 * 所有 Controller 必须继承，主要实现对客户端返回结果的封装
 * 参看项目内《业务结果代码范围控制》
 *
 * @author Tommy
 */
public abstract class BaseController {

    /**
     * 返回key：是否成功
     */
    private static final String RESULT_KEY_SUCCESS = "success";

    /**
     * 返回key：业务结果代码
     */
    private static final String RESULT_KEY_CODE = "code";

    /**
     * 返回key：业务信息
     */
    private static final String RESULT_KEY_MESSAGE = "message";

    /**
     * 返回key：业务数据
     */
    private static final String RESULT_KEY_DATA = "data";

    /**
     * 返回key：颁发token
     */
    private static final String RESULT_KEY_TOKEN = "token";


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
        return getJsonMsg(BussinessEnum.SUCCESS, CommonResponseEnum.SUCCESS, null);
    }

    /**
     * 返回操作成功，默认代码，有数据
     *
     * @param data 返回数据
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map successJsonMsg(Map data) {
        return getJsonMsg(BussinessEnum.SUCCESS, CommonResponseEnum.SUCCESS, data);
    }

    /**
     * 返回操作成功，预定义代码，无数据
     *
     * @param codeEnum 预定义代码枚举
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map successJsonMsg(ResponseEnum codeEnum) {
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
    protected static Map successJsonMsg(ResponseEnum codeEnum, Map data) {
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
        return getJsonMsg(BussinessEnum.FAIL, CommonResponseEnum.FAIL, null);
    }

    /**
     * 返回操作失败，默认代码，有数据
     *
     * @param data 返回数据
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map failJsonMsg(Map data) {
        return getJsonMsg(BussinessEnum.FAIL, CommonResponseEnum.FAIL, data);
    }

    /**
     * 返回操作失败，预定义代码，无数据
     *
     * @param codeEnum 预定义代码枚举
     * @return 返回Json包
     * @author Tommy
     */
    protected static Map failJsonMsg(ResponseEnum codeEnum) {
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
    protected static Map failJsonMsg(ResponseEnum codeEnum, Map data) {
        return getJsonMsg(BussinessEnum.FAIL, codeEnum, data);
    }

    /**
     * 向输出结果集中添加 user token 信息
     * @param jsonMsg 输出结果集
     * @param userId 用户id
     */
    protected static Map addTokenToJsonMsg(Map jsonMsg, long userId) {
        return ImmutableMap.of(RESULT_KEY_SUCCESS, jsonMsg.get(RESULT_KEY_SUCCESS),
                RESULT_KEY_CODE, jsonMsg.get(RESULT_KEY_CODE),
                RESULT_KEY_MESSAGE, jsonMsg.get(RESULT_KEY_MESSAGE),
                RESULT_KEY_DATA, jsonMsg.get(RESULT_KEY_DATA),
                RESULT_KEY_TOKEN, UserTokenHandler.createToken(userId)
        );
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
    private static Map getJsonMsg(@NotNull BussinessEnum successEnum, String msg, Map data) {
        return ImmutableMap.of(RESULT_KEY_SUCCESS, successEnum.getCode(),
                RESULT_KEY_CODE, CommonResponseEnum.CUSTOM.getCode(),
                RESULT_KEY_MESSAGE, msg,
                RESULT_KEY_DATA, data == null ? Collections.emptyMap() : data
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
    private static Map getJsonMsg(@NotNull BussinessEnum successEnum, @NotNull ResponseEnum codeEnum, Map data) {
        return ImmutableMap.of(RESULT_KEY_SUCCESS, successEnum.getCode(),
                RESULT_KEY_CODE, codeEnum.getCode(),
                RESULT_KEY_MESSAGE, codeEnum.getMessage(),
                RESULT_KEY_DATA, data == null ? Collections.emptyMap() : data
        );
    }


}
