package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import com.bookool.myboot.common.base.pageresult.PageList;
import com.bookool.myboot.common.enums.response.UserResponseEnum;
import com.bookool.myboot.common.exception.response.ResponseException;
import com.bookool.myboot.common.token.user.TokenVerify;
import com.bookool.myboot.domain.dto.param.UserBaseParam;
import com.bookool.myboot.domain.dto.result.UserBaseResult;
import com.bookool.myboot.service.UserBaseService;

import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.bookool.myboot.common.token.user.ActionEnum.SKIP;
import static com.bookool.myboot.common.token.user.ActionEnum.VERIFY;
import static com.bookool.myboot.common.token.user.UserTokenHandler.HEADER_NAME;
import static com.bookool.myboot.common.token.user.UserTokenHandler.REQUEST_ATTRIBUTE_KEY;

/**
 * 用户相关的api接口
 *
 * @author Tommy
 */
@RestController
@RequestMapping("/user/")
public class UserController extends BaseController {

    @Resource
    UserBaseService userBaseService;

    @ApiOperation("获取用户列表，测试ListPageByCustom")
    @RequestMapping(value = "/getuserlistbycustom", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map getUserListByCustom(@ApiParam(name = "user", value = "查询条件", required = true)
                                   @RequestBody UserBaseParam user) {
        PageList<UserBaseResult> userPage = userBaseService.getListPageByCustom(user);
        return successJsonMsg(ImmutableMap.of("users", userPage));
    }

    @ApiOperation("获取用户列表，测试ListPageByPageHelper")
    @RequestMapping(value = "/getuserlistbypagehelper", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map getUserListByPageHelper(@ApiParam(name = "user", value = "查询条件", required = true)
                                       @RequestBody UserBaseParam user) {
        PageList<UserBaseResult> userPage = userBaseService.getListPageByPageHelper(user);
        return successJsonMsg(ImmutableMap.of("users", userPage));
    }

    @ApiOperation("获取用户列表不分页")
    @RequestMapping(value = "/getuserlist", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map getUserList(@ApiParam(name = "user", value = "查询条件", required = true)
                           @RequestBody UserBaseParam user) {
        Long count = userBaseService.getListCount(user);
        List<UserBaseResult> userList = userBaseService.getList(user);
        return successJsonMsg(ImmutableMap.of(
                "count", count,
                "users", userList
        ));
    }

    @ApiOperation("添加一个用户")
    @RequestMapping(value = "/insertuser", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map insertUser(@ApiParam(name = "user", value = "用户信息", required = true)
                          @RequestBody UserBaseParam user) {
        user.setId(null);
        user.setGmtCreate(null);
        user.setGmtModified(null);
        user.setPasswordModified(new Date());
        if (userBaseService.insertSelective(user) == 1) {
            return successJsonMsg();
        } else {
            return failJsonMsg();
        }
    }

    @ApiOperation("编辑一个用户")
    @RequestMapping(value = "/updateuser", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map updateUser(@ApiParam(name = "user", value = "用户信息", required = true)
                          @RequestBody UserBaseParam user) {
        long id = user.getId();
        user.setId(null);
        user.setGmtCreate(null);
        user.setGmtModified(null);
        if (userBaseService.updateSelective(user, id) == 1) {
            return successJsonMsg();
        } else {
            return failJsonMsg();
        }
    }

    @ApiOperation("用户登录")
    @RequestMapping(value = "/userlogin", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map userLogin(@ApiParam(name = "loginName", value = "登录名", required = true)
                         @RequestParam(value = "loginName") String loginName,
                         @ApiParam(name = "password", value = "密码", required = true)
                         @RequestParam(value = "password") String password) {
        long id;
        try {
            id = userBaseService.getIdByLoginWithPassword(loginName, password);
        } catch (ResponseException e) {
            return failJsonMsg(e.getResponseEnum());
        }
        Map remap = successJsonMsg(UserResponseEnum.LOGIN_SUCCESS);
        return addTokenToJsonMsg(remap, id);
    }

    @ApiOperation("获取当前登录用户信息")
    @RequestMapping(value = "/getinfo", method = RequestMethod.POST)
    @TokenVerify(VERIFY)
    @ApiImplicitParam(name = HEADER_NAME, value = "用户Token", required = true, dataType = "String",
            paramType = "header")
    public Map getInfo(HttpServletRequest request) {
        long id = (long) request.getAttribute(REQUEST_ATTRIBUTE_KEY);
        return successJsonMsg(ImmutableMap.of(
                "userInfo", userBaseService.getUserById(id)));
    }

}
