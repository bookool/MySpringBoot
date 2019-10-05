package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import com.bookool.myboot.common.base.SnowflakeIdHandler;
import com.bookool.myboot.common.token.user.TokenVerify;
import com.bookool.myboot.elasticsearch.document.UserDocument;
import com.bookool.myboot.elasticsearch.service.UserEsService;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.bookool.myboot.common.token.user.ActionEnum.SKIP;

/**
 * ES测试id.index
 *
 * @author Tommy
 */
@RestController
@RequestMapping("/elastaticsearch/")
public class ElastaticsearchController extends BaseController {

    private Logger log = LoggerFactory.getLogger(ElastaticsearchController.class);

    @Resource
    private UserEsService userEsService;

    @TokenVerify(SKIP)
    @ApiOperation("测试es put mapping")
    @RequestMapping(value = "/testesputmapping", method = RequestMethod.POST)
    public Map testEsPutMapping() {
        if (userEsService.putMapping(UserDocument.class)) {
            return successJsonMsg();
        } else {
            return failJsonMsg();
        }
    }

    @ApiOperation("测试es 添加用户")
    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map saveUser(@ApiParam(name = "user", value = "待添加用户", required = true)
                        @RequestBody UserDocument user) {
        user.setId(SnowflakeIdHandler.nextId().toString());
        if (userEsService.save(user)) {
            // 如果多个
            // userEsService.saveAll(Arrays.asList(user, user));
            return successJsonMsg();
        } else {
            return failJsonMsg();
        }
    }

    @ApiOperation("测试es 删除用户")
    @RequestMapping(value = "/deluser", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map deleteUser(@ApiParam(name = "userId", value = "待删除用户Id", required = true, example = "123")
                          @RequestParam String userId) {
        if (userEsService.deleteById(userId)) {
            return successJsonMsg();
        } else {
            return failJsonMsg();
        }
    }

    @ApiOperation("测试es 获取用户")
    @RequestMapping(value = "/getuser", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map getUser(@ApiParam(name = "userId", value = "获取用户Id", required = true, example = "123")
                       @RequestParam String userId) {
        UserDocument userDocument = userEsService.getById(userId);
        return successJsonMsg(ImmutableMap.of("user", userDocument));
    }

    @ApiOperation("测试es 获取用户通过用户名")
    @RequestMapping(value = "/getuserbyusername", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map getUserByUsername(@ApiParam(name = "userName", value = "用户名", required = true, example = "用户名")
                                 @RequestParam String userName) {
        List<UserDocument> userList = userEsService.getByUserName(userName);
        return successJsonMsg(ImmutableMap.of("user", userList));
    }

    @ApiOperation("测试es 获取用户通过手机号")
    @RequestMapping(value = "/getuserbyusermobile", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map getUserByMobile(@ApiParam(name = "mobile", value = "手机号", required = true, example = "手机号")
                               @RequestParam String mobile) {
        List<UserDocument> userList = userEsService.getByMobile(mobile);
        return successJsonMsg(ImmutableMap.of("user", userList));
    }

    @ApiOperation("测试es 获取用户通过用户名昵称分页")
    @RequestMapping(value = "/getuserbyusernamepage", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map getUserByUsernamePage(@ApiParam(name = "userName", value = "用户名", required = true, example = "用户名")
                                     @RequestParam String userName,
                                     @ApiParam(name = "page", value = "当前页", required = true, example = "1")
                                     @RequestParam int page,
                                     @ApiParam(name = "size", value = "每页数", required = true, example = "1")
                                     @RequestParam int size) {
        List<UserDocument> userList = userEsService.getByUserNamePage(userName, page, size);
        return successJsonMsg(ImmutableMap.of("user", userList));
    }

}
