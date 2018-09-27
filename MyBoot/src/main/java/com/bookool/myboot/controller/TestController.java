package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import com.bookool.myboot.common.base.JwtHandler;
import com.bookool.myboot.common.base.SnowflakeIdHandler;
import com.bookool.myboot.common.enums.response.CommonCodeEnum;
import com.bookool.myboot.common.utils.CachedBeanCopier;
import com.bookool.myboot.domain.dto.result.UserBaseResult;
import com.bookool.myboot.domain.entity.UserBase;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Tommy
 * @date 2018-08-05
 */
@RestController
@RequestMapping("/test/")
public class TestController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/getjwttest", method = RequestMethod.POST)
    public Map getJwtTest() {
        Map<String, Object> jwt = new HashMap<>(1);
        jwt.put("userid", 1234567890);
        Map<String, Object> remap = new HashMap<>(1);
        remap.put("jwt", JwtHandler.createToken(jwt));
        return successJsonMsg(CommonCodeEnum.SUCCESS, remap);
    }

    @RequestMapping(value = "/getsnowtest", method = RequestMethod.POST)
    public Map getSnowTest() {
        Map<String, Object> remap = new HashMap<>(3);
        TestSnowClass tClass = new TestSnowClass();
        tClass.ths = new Thread[100];
        for (int i = 0; i< 100; i++) {
            tClass.ths[i] = new Thread(() -> tClass.runSnowTest());
            tClass.ths[i].start();
        }
        for (int i = 0; i< 100; i++) {
            while (tClass.ths[i].getState() == Thread.State.BLOCKED) {
                Thread.yield();
            }
        }
        synchronized (tClass.tSnow) {
            tClass.runStart = true;
            tClass.tSnow.notifyAll();
        }
        for (int i = 0; i< 100; i++) {
            try {
                tClass.ths[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        remap.put("idscount", tClass.tSnow.size());
        remap.put("ids", tClass.tSnow);
        return successJsonMsg(remap);
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

    @ApiOperation("测试日志")
    @RequestMapping(value = "/testlog", method = RequestMethod.POST)
    public Map testLog() {
        log.trace("测试日志：level=trace");
        log.debug("测试日志：level=debug");
        log.info("测试日志：level=info");
        log.warn("测试日志：level=warn");
        log.error("测试日志：level=error");
        Map<String, Object> remap = new HashMap<>(0);
        return successJsonMsg(remap);
    }

    @ApiOperation("测试Bean拷贝器")
    @RequestMapping(value = "/testcopier", method = RequestMethod.POST)
    public Map testCopier() {
        UserBase ub = new UserBase();
        ub.setId(123L);
        ub.setEmail("Email");
        ub.setMobile("Mobile");
        UserBaseResult ubr = CachedBeanCopier.copyToNew(ub, UserBaseResult.class);
        Map<String, Object> remap = new HashMap<>(0);
        remap.put("user", ubr);
        return successJsonMsg(remap);
    }

}
