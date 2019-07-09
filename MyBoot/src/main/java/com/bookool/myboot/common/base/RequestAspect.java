package com.bookool.myboot.common.base;

import com.bookool.myboot.common.enums.response.base.CommonResponseEnum;
import com.bookool.myboot.common.token.user.ActionEnum;
import com.bookool.myboot.common.token.user.TokenVerify;
import com.bookool.myboot.common.token.user.UserTokenHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

import static com.bookool.myboot.common.token.user.UserTokenHandler.HEADER_NAME;
import static com.bookool.myboot.common.token.user.UserTokenHandler.REQUEST_ATTRIBUTE_KEY;

/**
 * Controller 请求的切面
 * 主要实现对 user token 的验证，并从中解析出 user id
 *
 * @author Tommy
 */
@Component
@Aspect
public class RequestAspect {

    @Pointcut("execution(public java.util.Map com.bookool.myboot.controller..*.*(..))")
    public void requestPointcut() {
        // Do nothing
    }

    @Around("requestPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 取得request对象
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
            Enumeration<String> enumeration = request.getHeaderNames();
            String userToken = null;
            Long userId = null;
            Boolean renewalToken = false;
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement().toUpperCase();
                // 找到名为 USER-TOKEN 的 header ，拿到 user token
                if (name.equals(HEADER_NAME)) {
                    userToken = request.getHeader(name);
                    break;
                }
            }
            if (userToken != null) {
                // 如果有 user token ，则解析，取出 user id 和是否要重新颁发 token
                Pair<Long, Boolean> userPair = UserTokenHandler.getUserId(userToken);
                if (userPair != null) {
                    userId = userPair.getLeft();
                    renewalToken = userPair.getRight();
                }
            }
            TokenVerify tokenVerify = ((MethodSignature) joinPoint.getSignature()).getMethod()
                    .getAnnotation(TokenVerify.class);
            if (tokenVerify != null) {
                ActionEnum action = tokenVerify.value();
                // 如果需要验证登录
                if (ActionEnum.VERIFY.equals(action)) {
                    // token 或拿到的用户无效
                    if (userId == null) {
                        // 返回需要登录的信息
                        return BaseController.failJsonMsg(CommonResponseEnum.NOT_LOGIN);
                    }
                }
            }
            // 把 user id 放到 request 中，以便后续使用
            request.setAttribute(REQUEST_ATTRIBUTE_KEY, userId);
            Map reval = (Map) joinPoint.proceed();
            // 如果需要重新颁发 token
            if (renewalToken) {
                // 重新颁发 token
                reval = BaseController.addTokenToJsonMsg(reval, userId);
            }
            return reval;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
