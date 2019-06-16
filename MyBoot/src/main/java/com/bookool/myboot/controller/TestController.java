package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import com.bookool.myboot.common.base.SnowflakeIdHandler;
import com.bookool.myboot.common.enums.response.base.CommonResponseEnum;
import com.bookool.myboot.common.token.user.TokenVerify;
import com.bookool.myboot.common.token.user.UserTokenHandler;
import com.bookool.myboot.common.utils.CachedBeanCopier;
import com.bookool.myboot.domain.dto.result.UserResult;
import com.bookool.myboot.domain.entity.User;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.bookool.myboot.common.token.user.ActionEnum.SKIP;

/**
 * 一些方法测试
 *
 * @author Tommy
 */
@RestController
@RequestMapping("/test/")
public class TestController extends BaseController {

    private Logger log = LoggerFactory.getLogger(TestController.class);

    @TokenVerify(SKIP)
    @RequestMapping(value = "/getjwttest", method = RequestMethod.POST)
    public Map getJwtTest() {
        return successJsonMsg(CommonResponseEnum.SUCCESS, ImmutableMap.of(
                "userid", 1234567890,
                "jwt", UserTokenHandler.createToken(1234567890L)));
    }

    @TokenVerify(SKIP)
    @RequestMapping(value = "/getsnowtest", method = RequestMethod.POST)
    public Map getSnowTest() {
        TestSnowClass tClass = new TestSnowClass();
        tClass.ths = new Thread[100];
        for (int i = 0; i < 100; i++) {
            tClass.ths[i] = new Thread(() -> tClass.runSnowTest());
            tClass.ths[i].start();
        }
        for (int i = 0; i < 100; i++) {
            while (tClass.ths[i].getState() == Thread.State.BLOCKED) {
                Thread.yield();
            }
        }
        synchronized (tClass.tSnow) {
            tClass.runStart = true;
            tClass.tSnow.notifyAll();
        }
        for (int i = 0; i < 100; i++) {
            try {
                tClass.ths[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return successJsonMsg(ImmutableMap.of(
                "idscount", tClass.tSnow.size(),
                "ids", tClass.tSnow
        ));
    }

    private class TestSnowClass {
        final Set<Long> tSnow = new TreeSet<>();
        boolean runStart;
        Thread[] ths;

        void runSnowTest() {
            synchronized (tSnow) {
                while (!runStart) {
                    try {
                        tSnow.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Long id = SnowflakeIdHandler.nextId();
                log.info(id.toString());
                tSnow.add(id);
            }
        }
    }

    @TokenVerify(SKIP)
    @ApiOperation("测试日志")
    @RequestMapping(value = "/testlog", method = RequestMethod.POST)
    public Map testLog() {
        log.trace("测试日志：level=trace");
        log.debug("测试日志：level=debug");
        log.info("测试日志：level=info");
        log.warn("测试日志：level=warn");
        log.error("测试日志：level=error");
        return successJsonMsg(ImmutableMap.of());
    }

    @TokenVerify(SKIP)
    @ApiOperation("测试Bean拷贝器")
    @RequestMapping(value = "/testcopier", method = RequestMethod.POST)
    public Map testCopier() {
        User user = new User();
        user.setId(123L);
        user.setMobile("Mobile");
        UserResult userResult = CachedBeanCopier.copyToNew(user, UserResult.class);
        return successJsonMsg(ImmutableMap.of("user", userResult));
    }

}
