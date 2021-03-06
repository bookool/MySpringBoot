package com.bookool.myboot.test;

import com.bookool.myboot.domain.dto.result.UserResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 测试用的 User 相关方法
 * @author Tommy
 */
public class User {
    public static String getNewUserJsonForTest() {
        UserResult user = new UserResult();
        user.setMobile("789");
        user.setId(1234856789L);
        ObjectMapper mapper = new ObjectMapper();
        // 为NULL或者为空不参加序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(user);
        } catch (Exception e) {
            return null;
        }
    }
}
