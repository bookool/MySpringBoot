package com.bookool.myboot.common.base.fastpageresult;

/**
 * 信息更新较快的分页列表时的异常
 *
 * @author Tommy
 */
public class FastPageListException extends RuntimeException {

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 构造一个基本异常,格式化字符串.
     *
     * @param message 信息描述
     * @param args 格式化对象
     */
    public FastPageListException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * 构造一个基本异常.
     *
     * @param message 信息描述
     */
    public FastPageListException(String message) {
        super(message);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 错误编码
     * @param message   信息描述
     */
    public FastPageListException(String errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 错误编码
     * @param message   信息描述
     */
    public FastPageListException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.setErrorCode(errorCode);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message 信息描述
     * @param cause   根异常类（可以存入任何异常）
     */
    public FastPageListException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 获取错误编码
     *
     * @return 错误编码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误编码
     *
     * @param errorCode 设置错误编码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
