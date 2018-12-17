package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import com.bookool.myboot.common.base.RocketmqSender;
import com.bookool.myboot.common.token.user.TokenVerify;
import com.bookool.myboot.test.User;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

import static com.bookool.myboot.common.config.RocketmqConfig.*;
import static com.bookool.myboot.common.token.user.ActionEnum.SKIP;

/**
 * rocketmq消息测试
 *
 * @author Tommy
 */
@RestController
@RequestMapping("/rocketmq/")
public class RocketmqController extends BaseController {

    private Logger log = LoggerFactory.getLogger(RocketmqController.class);

    @Resource
    RocketmqSender rocketmqSender;

    @TokenVerify(SKIP)
    @ApiOperation("rocketmq发送消息测试")
    @RequestMapping(value = "/senduser", method = RequestMethod.POST)
    public Map sendUser() {
        String userJson = User.getNewUserJsonForTest();
        if (userJson == null) {
            return failJsonMsg();
        }
        rocketmqSender.send(userJson, GROUP_NAME, TOPIC_USER, TAG_USER);
        return successJsonMsg();
    }

}
