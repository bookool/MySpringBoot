package com.bookool.myboot.elasticsearch.service;

import com.bookool.myboot.elasticsearch.document.UserDocument;

import java.util.List;

/**
 * 用户搜索
 *
 * @author Tommy
 * @date 190927
 */
public interface UserEsService {
    /**
     * 建立Type(表)
     *
     * @param clazz doc
     * @return 是否成功
     */
    <T> boolean putMapping(Class<T> clazz);

    /**
     * 添加一个用户
     *
     * @param userDocument 用户实体
     * @return 是否成功
     */
    boolean save(UserDocument userDocument);

    /**
     * 添加多个用户
     *
     * @param userDocuments 用户实体列表
     * @return 是否成功
     */
    boolean saveAll(List<UserDocument> userDocuments);

    /**
     * 删除一个用户
     *
     * @param userId 用户id
     * @return 是否成功
     */
    boolean deleteById(String userId);

    /**
     * 获取一个用户
     *
     * @param userId 用户id
     * @return 获取的用户
     */
    UserDocument getById(String userId);

    /**
     * 获取用户通过用户名
     *
     * @param userName 用户名
     * @return 获取的用户
     */
    List<UserDocument> getByUserName(String userName);

    /**
     * 获取用户通过手机号
     *
     * @param mobile 手机号
     * @return 获取的用户
     */
    List<UserDocument> getByMobile(String mobile);

    /**
     * 获取用户通过用户名（分页）
     *
     * @param userName 用户名
     * @param page 页数
     * @param size 每页数量
     * @return 获取的用户
     */
    List<UserDocument> getByUserNamePage(String userName, int page, int size);

}
