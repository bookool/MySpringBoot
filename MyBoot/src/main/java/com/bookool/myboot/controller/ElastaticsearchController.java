package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import com.bookool.myboot.common.base.SnowflakeIdHandler;
import com.bookool.myboot.common.token.user.TokenVerify;
import com.bookool.myboot.elasticsearch.document.UserDocument;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

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
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private ElasticsearchRepository<UserDocument, Long> userDocumentRepository;

    @TokenVerify(SKIP)
    @ApiOperation("测试es put mapping")
    @RequestMapping(value = "/testesputmapping", method = RequestMethod.POST)
    public Map testEsPutMapping() {
        elasticsearchTemplate.putMapping(UserDocument.class);
        return successJsonMsg(ImmutableMap.of());
    }

    @ApiOperation("测试es 添加用户")
    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    @TokenVerify(SKIP)
    public Map saveUser(@ApiParam(name = "user", value = "待添加用户", required = true)
                                       @RequestBody UserDocument user) {
        user.setId(SnowflakeIdHandler.nextId().toString());
        userDocumentRepository.save(user);
        // 如果多个
        // userDocumentRepository.saveAll(Arrays.asList(user, user));
        return successJsonMsg(ImmutableMap.of());

    }

}
