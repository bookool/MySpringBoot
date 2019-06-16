package com.bookool.myboot.service;

import com.bookool.myboot.common.base.fastpageresult.FastPageList;
import com.bookool.myboot.common.base.pageresult.PageList;
import com.bookool.myboot.common.exception.response.ResponseException;
import com.bookool.myboot.domain.dto.param.UserParam;
import com.bookool.myboot.domain.dto.result.UserResult;

import java.util.Date;
import java.util.List;

/**
 * UserService 用户基础信息表 Service
 *
 * @author Tommy
 * @date 2019-05-25 18:36:33
 */
public interface UserService {

    // #####################以下为自动生成的基础模板代码############################################################

    /**
     * 基础模板 取得符合条件的记录个数
     *
     * @param userParam 查询条件对象
     * @return 符合条件的记录个数
     */
    long getListCount(UserParam userParam);

    /**
     * 基础模板 取得符合条件的记录列表
     *
     * @param userParam 查询条件对象
     * @return 符合条件的记录列表
     */
    List<UserResult> getList(UserParam userParam);

    /**
     * 基础模板 取得 User 分页列表
     *
     * @param userParam 查询条件对象
     * @return 分页列表对象
     */
    PageList<UserResult> getListPageByCustom(UserParam userParam);

    /**
     * 基础模板 取得 User 分页列表，通过 PageHelper
     *
     * @param userParam 查询条件对象
     * @return 分页列表对象
     */
    PageList<UserResult> getListPageByPageHelper(UserParam userParam);

    /**
     * 基础模板 取得 User 快速更新分页列表
     *
     * @param userParam 查询条件对象
     * @return 分页列表对象
     */
    FastPageList<UserResult> getListFastPage(UserParam userParam);

    /**
     * 基础模板 添加 User
     * 本项目不使用数据库自增字段，因此避免使用任何与自增相关的方法
     * 本项目必须使用snowflakeId生产全局ID
     * 此方法自动生成 id、gmtCreate、gmtModified
     * 传入的添加对象应该确保 id、gmtCreate、gmtModified 为空
     *
     * @param userParam 要添加的对象
     * @return 影响行数
     * @throws ResponseException 传递异常
     */
    int insertSelective(UserParam userParam) throws ResponseException;

    /**
     * 基础模板 添加 User
     * 本项目不使用数据库自增字段，因此避免使用任何与自增相关的方法
     * 本项目必须使用snowflakeId生产全局ID
     * 此方法要求传入主键 id 值，自动生成 gmtCreate、gmtModified
     * 使用场景：对于某类信息的扩展表，主键id和主表的id保持一致，则不用生成id，直接拿主表id值来添加
     * 传入的添加对象应该确保 id 有值，gmtCreate、gmtModified 为空
     *
     * @param userParam 要添加的对象
     * @return 影响行数
     * @throws ResponseException 传递异常
     */
    int insertWithIdSelective(UserParam userParam) throws ResponseException;

    /**
     * 基础模板 编辑 User 利用Weekend传递条件参数，此处传主键id
     * 这是一个例子，主要是演示Weekend条件用法，具体业务请仿照此例子添加新方法
     *
     * @param userParam 要更新的记录
     * @param id    要更新的主键id值
     * @return 数据库执行完毕影响的行数
     * @throws ResponseException 传递异常
     */
    int updateSelective(UserParam userParam, long id) throws ResponseException;

    /**
     * 基础模板 编辑 User 通过主键id，id直接包含在传参对象内
     *
     * @param userParam 要更新的记录
     * @return 数据库执行完毕影响的行数
     * @throws ResponseException 传递异常
     */
    int updateByIdSelective(UserParam userParam) throws ResponseException;

    // ^^^^^^^^^^^^^^^^^^^^^以上为自动生成的基础模板代码^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


    /**
     * 解开 user token 后，检查用户是否有效
     *
     * @param id         userId
     * @param createTime Token创建时间
     * @return 用户是否有效
     */
    boolean isUserEnable(long id, Date createTime);

    /**
     * 用户登录
     *
     * @param loginName 登录名，可能为手机号码、用户名、用户email
     * @param password 用户密码
     * @return 用户Id
     * @throws ResponseException 用户操作相关异常
     */
    long getIdByLoginWithPassword(String loginName, String password) throws ResponseException;

    /**
     * 通过 id 获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    UserResult getUserById(long id);

}
