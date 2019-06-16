package com.bookool.myboot.service.impl;

import com.bookool.myboot.common.base.fastpageresult.FastPageList;
import com.bookool.myboot.common.base.pageresult.BasePageList;
import com.bookool.myboot.common.base.pageresult.PageList;
import com.bookool.myboot.common.enums.UserStateEnum;
import com.bookool.myboot.common.enums.response.UserResponseEnum;
import com.bookool.myboot.common.enums.response.base.CommonResponseEnum;
import com.bookool.myboot.common.exception.response.ResponseException;
import com.bookool.myboot.common.utils.CachedBeanCopier;
import com.bookool.myboot.mapper.UserMapper;
import com.bookool.myboot.domain.dto.param.UserParam;
import com.bookool.myboot.domain.dto.result.UserResult;
import com.bookool.myboot.domain.entity.User;
import com.bookool.myboot.service.UserService;

import com.bookool.myboot.service.impl.base.FastPageListImpl;
import com.bookool.myboot.service.impl.base.PageListImplByCustom;
import com.bookool.myboot.service.impl.base.PageListImplByPageHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * UserServiceImpl 用户基础信息表 Service 实现
 *
 * @author Tommy
 * @date 2019-05-25 18:36:33
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    // #####################以下为自动生成的基础模板代码############################################################

    /**
     * 基础模板 取得符合条件的记录个数
     *
     * @param userParam 查询条件对象
     * @return 符合条件的记录个数
     */
    @Override
    public long getListCount(UserParam userParam) {
        return userMapper.getListCount(userParam);
    }

    /**
     * 基础模板 取得符合条件的记录列表
     *
     * @param userParam 查询条件对象
     * @return 符合条件的记录列表
     */
    @Override
    public List<UserResult> getList(UserParam userParam) {
        //虽然不分页，但必须避免请求巨大的结果集，由PAGE_SIZE_MAX限制
        userParam.getPageParam().setPageNum(BasePageList.PAGE_NUM_MIN);
        userParam.getPageParam().setPageSize(BasePageList.PAGE_SIZE_MAX);
        return userMapper.getList(userParam);
    }

    /**
     * 基础模板 取得 User 分页列表
     *
     * @param userParam 查询条件对象
     * @return 分页列表对象
     */
    @Override
    public PageList<UserResult> getListPageByCustom(UserParam userParam) {
        /*
         使用PageListByCustom创建分页对象，
         第一个参数：传递查询条件对象，如果需要分页，必须设置pageNum和pageSize参数；
         可以不分页，则pageNum和pageSize参数必须为空，且返回记录总数必须小于BasePageList.PAGE_SIZE_MAX，则返回符合条件的全部；
         第二个参数：设置返回符合条件记录总数的方法；
         第三个参数：设置返回符合条件列表的方法；
         上述两个方法会在PageListByCustom.Builder.builder中，根据实际需要执行。
         */
        return new PageListImplByCustom<>(userParam,
                t -> userMapper.getListCount(t),
                t -> userMapper.getListPage(t));
    }

    /**
     * 基础模板 取得 User 分页列表，通过 PageHelper
     *
     * @param userParam 查询条件对象
     * @return 分页列表对象
     */
    @Override
    public PageList<UserResult> getListPageByPageHelper(UserParam userParam) {
        /*
         使用PageListImplByPageHelper创建分页对象，
         第一个参数：传递查询条件对象，必须设置pageNum和pageSize参数；
         第二个参数：设置返回符合条件列表的方法；
         此方法自动调用PageHelper完成分页，并自动填充分页结果。
         */
        return new PageListImplByPageHelper<>(userParam, t -> userMapper.getList(t));
    }

    /**
    * 基础模板 取得 User 快速更新分页列表
    *
    * @param userParam 查询条件对象
    * @return 分页列表对象
    */
    @Override
    public FastPageList<UserResult> getListFastPage(UserParam userParam) {
        /*
         使用FastPageListImpl创建分页对象，
         第一个参数：传递查询条件对象，必须设置roundMinId、roundMaxId和pageSize参数；
         第二个参数：设置返回符合条件列表的方法
         */
        return new FastPageListImpl<>(userParam, t -> userMapper.getList(t));
    }

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
    @Override
    public int insertSelective(UserParam userParam) throws ResponseException {
        try {
            return userMapper.insertSelectiveGenerate(userParam, User.class);
        } catch (DuplicateKeyException ex) {
            throw new ResponseException(CommonResponseEnum.DATA_DUPLICATE);
        }
    }

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
    @Override
    public int insertWithIdSelective(UserParam userParam) throws ResponseException {
        try {
            return userMapper.insertWithIdSelectiveGenerate(userParam, User.class);
        } catch (DuplicateKeyException ex) {
            throw new ResponseException(CommonResponseEnum.DATA_DUPLICATE);
        }
    }

    /**
    * 基础模板 编辑 User 利用Weekend传递条件参数，此处传主键id
    * 这是一个例子，主要是演示Weekend条件用法，具体业务请仿照此例子添加新方法
    *
    * @param userParam 要更新的记录
    * @param id    要更新的主键id值
    * @return 数据库执行完毕影响的行数
    * @throws ResponseException 传递异常
    */
    @Override
    public int updateSelective(UserParam userParam, long id) throws ResponseException {
        Weekend<User> weekend = new Weekend<>(User.class);
        WeekendCriteria<User, Object> criteria = weekend.weekendCriteria();
        criteria.andEqualTo(User::getId, id);
        /// 如果需要更复杂的带括号的逻辑条件，可以用如下方式组合条件
        /// WeekendCriteria<User, Object> criteria1 = weekend.createCriteriaAddOn();
        /// criteria1.andEqualTo(User::getId, id).orGreaterThan(User::getId, id);
        /// weekend.and(criteria1);
        try {
            return userMapper.updateByWeekendSelectiveGenerate(userParam, User.class, weekend);
        } catch (DuplicateKeyException ex) {
            throw new ResponseException(CommonResponseEnum.DATA_DUPLICATE);
        }
    }

    /**
     * 基础模板 编辑 User 通过主键id，id直接包含在传参对象内
     *
     * @param userParam 要更新的记录
     * @return 数据库执行完毕影响的行数
     * @throws ResponseException 传递异常
     */
    @Override
    public int updateByIdSelective(UserParam userParam) throws ResponseException {
        try {
            return userMapper.updateByPrimaryKeySelectiveGenerate(userParam, User.class);
        } catch (DuplicateKeyException ex) {
            throw new ResponseException(CommonResponseEnum.DATA_DUPLICATE);
        }
    }

    // ^^^^^^^^^^^^^^^^^^^^^以上为自动生成的基础模板代码^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


    /**
     * 解开 user token 后，检查用户是否有效
     *
     * @param id         userId
     * @param createTime Token创建时间
     * @return 用户是否有效
     */
    @Override
    public boolean isUserEnable(long id, Date createTime) {
        Weekend<User> weekend = new Weekend<>(User.class);
        WeekendCriteria<User, Object> criteria = weekend.weekendCriteria();
        // id必须存在
        criteria.andEqualTo(User::getId, id);
        // 用户状态为有效
        criteria.andEqualTo(User::getUserState, UserStateEnum.ENABLE.code());
        // 用户修改密码的时间必须小于token创建时间
        criteria.andLessThan(User::getPasswordModified, createTime);
        return userMapper.selectCountByExample(weekend) == 1;
    }

    /**
     * 用户登录
     *
     * @param loginName 登录名，可能为手机号码、用户名、用户email
     * @param password 用户密码
     * @return 用户Id
     * @throws ResponseException 用户操作相关异常
     */
    @Override
    public long getIdByLoginWithPassword(String loginName, String password) throws ResponseException {
        Weekend<User> weekend = new Weekend<>(User.class);
        WeekendCriteria<User, Object> criteria = weekend.weekendCriteria();
        criteria.orEqualTo(User::getMobile, loginName);
        criteria.orEqualTo(User::getUserName, loginName);
        List<User> userList = userMapper.selectByExample(weekend);
        User user;
        if (userList.size() == 0) {
            // 用户不存在
            throw new ResponseException(UserResponseEnum.USER_NOT_EXIST);
        } else {
            user = userList.get(0);
            // 用户被禁用
            if (user.getUserState().equals(UserStateEnum.DISABLE.code())) {
                throw new ResponseException(UserResponseEnum.USER_DISABLE);
            }
            // 密码错误
            String pd = DigestUtils.md5Hex(password);
            if (!pd.equals(user.getUserPassword())) {
                throw new ResponseException(UserResponseEnum.PASSWORD_ERROR);
            }
        }
        return user.getId();
    }

    /**
     * 通过 id 获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @Override
    public UserResult getUserById(long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return null;
        } else {
            // 去掉敏感信息
            user.setPasswordModified(null);
            user.setUserPassword(null);
            return CachedBeanCopier.copyToNew(user, UserResult.class);
        }
    }

}
