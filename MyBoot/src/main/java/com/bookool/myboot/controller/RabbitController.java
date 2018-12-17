package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import com.bookool.myboot.common.base.RabbitSender;
import com.bookool.myboot.common.config.RabbitConfig;
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

import static com.bookool.myboot.common.token.user.ActionEnum.SKIP;

/**
 * rabbit消息测试
 *
 * @author Tommy
 */
@RestController
@RequestMapping("/rabbit/")
public class RabbitController extends BaseController {

    private Logger log = LoggerFactory.getLogger(RabbitController.class);

    @Resource
    RabbitSender rabbitSender;

    @TokenVerify(SKIP)
    @ApiOperation("rabbit发送消息测试")
    @RequestMapping(value = "/senduser", method = RequestMethod.POST)
    public Map sendUser() {
        String userJson = User.getNewUserJsonForTest();
        if (userJson == null) {
            return failJsonMsg();
        }
        rabbitSender.send(RabbitConfig.QUEUE_USER, userJson);
        return successJsonMsg();
    }

}
