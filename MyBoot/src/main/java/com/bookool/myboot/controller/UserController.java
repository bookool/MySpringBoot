package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import com.bookool.myboot.common.base.pageresult.PageList;
import com.bookool.myboot.domain.dto.param.UserBaseParam;
import com.bookool.myboot.domain.dto.result.UserBaseResult;
import com.bookool.myboot.service.UserBaseService;
import com.bookool.myboot.common.base.SnowflakeIdHandler;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tommy
 * @date 2018-08-05
 */
@RestController
@RequestMapping("/user/")
public class UserController extends BaseController {

    @Resource
    UserBaseService userBaseService;

    @ApiOperation("获取用户列表，测试ListPageByCustom")
    @RequestMapping(value = "/getuserlistbycustom", method = RequestMethod.POST)
    public Map getUserListByCustom(@ApiParam(name = "user", value = "查询条件", required = true)
                                   @RequestBody UserBaseParam user) {
        Map<String, Object> remap = new HashMap<>(1);
        PageList<UserBaseResult> userPage = userBaseService.getListPageByCustom(user);
        remap.put("users", userPage);
        return successJsonMsg(remap);
    }

    @ApiOperation("获取用户列表，测试ListPageByPageHelper")
    @RequestMapping(value = "/getuserlistbypagehelper", method = RequestMethod.POST)
    public Map getUserListByPageHelper(@ApiParam(name = "user", value = "查询条件", required = true)
                                       @RequestBody UserBaseParam user) {
        Map<String, Object> remap = new HashMap<>(1);
        PageList<UserBaseResult> userPage = userBaseService.getListPageByPageHelper(user);
        remap.put("users", userPage);
        return successJsonMsg(remap);
    }

    @ApiOperation("获取用户列表不分页")
    @RequestMapping(value = "/getuserlist", method = RequestMethod.POST)
    public Map getUserList(@ApiParam(name = "user", value = "查询条件", required = true)
                           @RequestBody UserBaseParam user) {
        Map<String, Object> remap = new HashMap<>(1);
        Long count = userBaseService.getListCount(user);
        List<UserBaseResult> userList = userBaseService.getList(user);
        remap.put("count", count);
        remap.put("users", userList);
        return successJsonMsg(remap);
    }

    @ApiOperation("添加一个用户")
    @RequestMapping(value = "/insertuser", method = RequestMethod.POST)
    public Map insertUser(@ApiParam(name = "user", value = "用户信息", required = true)
                          @RequestBody UserBaseParam user) {
        user.setId(null);
        user.setGmtCreate(null);
        user.setGmtModified(null);
        if (userBaseService.insertSelective(user) == 1) {
            return successJsonMsg();
        } else {
            return failJsonMsg();
        }
    }

    @ApiOperation("编辑一个用户")
    @RequestMapping(value = "/updateuser", method = RequestMethod.POST)
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

}
