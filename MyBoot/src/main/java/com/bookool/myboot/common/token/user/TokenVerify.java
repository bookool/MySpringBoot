package com.bookool.myboot.common.token.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.bookool.myboot.common.token.user.ActionEnum.VERIFY;

/**
 * 是否需要验证 Token
 * 在 Controller 方法上加注解
 * 在 RequestAspect 中切面验证
 *
 * @author Tommy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenVerify {
    ActionEnum value() default VERIFY;
}
