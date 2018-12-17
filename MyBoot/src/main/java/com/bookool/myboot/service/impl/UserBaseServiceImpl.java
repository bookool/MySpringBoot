package com.bookool.myboot.service.impl;

import com.bookool.myboot.common.base.pageresult.BasePageList;
import com.bookool.myboot.common.base.pageresult.PageList;
import com.bookool.myboot.common.enums.UserStateEnum;
import com.bookool.myboot.common.enums.response.UserResponseEnum;
import com.bookool.myboot.common.exception.response.ResponseException;
import com.bookool.myboot.common.utils.CachedBeanCopier;
import com.bookool.myboot.mapper.UserBaseMapper;
import com.bookool.myboot.domain.dto.param.UserBaseParam;
import com.bookool.myboot.domain.dto.result.UserBaseResult;
import com.bookool.myboot.domain.entity.UserBase;
import com.bookool.myboot.service.UserBaseService;

import com.bookool.myboot.service.impl.base.PageListByCustom;
import com.bookool.myboot.service.impl.base.PageListByPageHelper;
import com.bookool.myboot.service.impl.base.PageListParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * UserBaseServiceImpl 用户基础信息表 Service 实现
 *
 * @author Tommy
 */
@Service("UserBaseService")
public class UserBaseServiceImpl implements UserBaseService {

    @Resource
    private UserBaseMapper userBaseMapper;

    // #####################以下为自动生成的基础模板代码############################################################
    //region >>>自动生成的基础模板代码<<<

    /**
     * 基础模板 取得符合条件的记录个数
     *
     * @param param 条件dto
     * @return 符合条件的记录个数
     */
    @Override
    public Long getListCount(UserBaseParam param) {
        return userBaseMapper.getListCount(param);
    }

    /**
     * 基础模板 取得符合条件的记录列表不分页，但仍然被PAGE_SIZE_MAX限制
     *
     * @param param 条件dto
     * @return 符合条件的记录列表
     */
    @Override
    public List<UserBaseResult> getList(UserBaseParam param) {
        //虽然不分页，但必须避免请求巨大的结果集，由PAGE_SIZE_MAX限制
        param.setPageNum(BasePageList.PAGE_NUM_MIN);
        param.setPageSize(BasePageList.PAGE_SIZE_MAX);
        PageListParam<UserBaseParam> pageListParam = new PageListParam<>(param);
        return userBaseMapper.getList(pageListParam);
    }

    /**
     * 基础模板 取得 user_base 分页列表
     *
     * @param param 条件对象
     * @return 分页列表对象
     */
    @Override
    public PageList<UserBaseResult> getListPageByCustom(UserBaseParam param) {
        /*
        使用PageListByCustom创建分页对象，
        第一个参数：传递查询条件对象，如果需要分页，必须设置pageNum和pageSize参数；
        可以不分页，则pageNum和pageSize参数必须为空，且返回记录总数必须小于BasePageList.PAGE_SIZE_MAX，则返回符合条件的全部；
        第二个参数：设置返回符合条件记录总数的方法；
        第三个参数：设置返回符合条件列表的方法；
        上述两个方法会在PageListByCustom.Builder.builder中，根据实际需要执行。
         */
        return new PageListByCustom<>(param,
                t -> userBaseMapper.getListCount(t),
                t -> userBaseMapper.getListPage(t));
    }

    /**
     * 基础模板 取得 user_base 分页列表，通过 PageHelper
     *
     * @param param 条件对象
     * @return 分页列表对象
     */
    @Override
    public PageList<UserBaseResult> getListPageByPageHelper(UserBaseParam param) {
        /*
        使用PageListByPageHelper创建分页对象，
        第一个参数：传递查询条件对象，必须设置pageNum和pageSize参数；
        第二个参数：设置返回符合条件列表的方法；
        此方法自动调用PageHelper完成分页，并自动填充分页结果。
         */
        return new PageListByPageHelper<>(param, t -> userBaseMapper.getList(t));
    }

    /**
     * 基础模板 添加 user_base
     * 本项目不使用数据库自增字段，因此避免使用任何与自增相关的方法
     * 本项目必须使用snowflakeId生产全局ID
     * 此方法自动生成 id、gmtCreate、gmtModified
     * 传入的添加对象应该确保 id、gmtCreate、gmtModified 为空
     *
     * @param param 要添加的对象
     * @return 影响行数
     */
    @Override
    public int insertSelective(UserBaseParam param) {
        return userBaseMapper.insertSelectiveGenerate(param, UserBase.class);
    }

    /**
     * 基础模板 添加 user_base
     * 本项目不使用数据库自增字段，因此避免使用任何与自增相关的方法
     * 本项目必须使用 snowflakeId 生产全局ID
     * 此方法要求传入主键 id 值，自动生成 gmtCreate、gmtModified
     * 使用场景：对于某类信息的扩展表，主键id和主表的id保持一致，则不用生成id，直接拿主表id值来添加
     * 传入的添加对象应该确保 id 有值，gmtCreate、gmtModified 为空
     *
     * @param param 要添加的对象
     * @return 影响行数
     */
    @Override
    public int insertWithIdSelective(UserBaseParam param) {
        return userBaseMapper.insertWithIdSelectiveGenerate(param, UserBase.class);
    }

    /**
     * 基础模板 编辑 user_base 利用Weekend传递条件参数，此处传主键id
     * 这是一个例子，主要是演示Weekend条件用法，具体业务请仿照此例子添加新方法
     *
     * @param param 要更新的记录
     * @param id    要更新的主键id值
     * @return 数据库执行完毕影响的行数
     */
    @Override
    public int updateSelective(UserBaseParam param, long id) {
        Weekend<UserBase> weekend = new Weekend<>(UserBase.class);
        WeekendCriteria<UserBase, Object> criteria = weekend.weekendCriteria();
        criteria.andEqualTo(UserBase::getId, id);
        /// 如果需要更复杂的带括号的逻辑条件，可以用如下方式组合条件
        /// WeekendCriteria<UserBase, Object> criteria1 = weekend.createCriteriaAddOn();
        /// criteria1.andEqualTo(UserBase::getId, id).orGreaterThan(UserBase::getId, id);
        /// weekend.and(criteria1);
        return userBaseMapper.updateByWeekendSelectiveGenerate(param, UserBase.class, weekend);
    }

    /**
     * 基础模板 编辑 user_base 通过主键id，id直接包含在传参对象内
     *
     * @param param 要更新的记录
     * @return 数据库执行完毕影响的行数
     */
    @Override
    public int updateByIdSelective(UserBaseParam param) {
        return userBaseMapper.updateByPrimaryKeySelectiveGenerate(param, UserBase.class);
    }

    //endregion
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
        Weekend<UserBase> weekend = new Weekend<>(UserBase.class);
        WeekendCriteria<UserBase, Object> criteria = weekend.weekendCriteria();
        // id必须存在
        criteria.andEqualTo(UserBase::getId, id);
        // 用户状态为有效
        criteria.andEqualTo(UserBase::getUserState, UserStateEnum.ENABLE.code());
        // 用户修改密码的时间必须小于token创建时间
        criteria.andLessThan(UserBase::getPasswordModified, createTime);
        return userBaseMapper.selectCountByExample(weekend) == 1;
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
        Weekend<UserBase> weekend = new Weekend<>(UserBase.class);
        WeekendCriteria<UserBase, Object> criteria = weekend.weekendCriteria();
        criteria.orEqualTo(UserBase::getMobile, loginName);
        criteria.orEqualTo(UserBase::getUserName, loginName);
        criteria.orEqualTo(UserBase::getEmail, loginName);
        List<UserBase> userList = userBaseMapper.selectByExample(weekend);
        UserBase user;
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
    public UserBaseResult getUserById(long id) {
        UserBase ub = userBaseMapper.selectByPrimaryKey(id);
        if (ub == null) {
            return null;
        } else {
            // 去掉敏感信息
            ub.setPasswordModified(null);
            ub.setUserPassword(null);
            ub.setWxOpenid(null);
            ub.setWxUnionid(null);
            return CachedBeanCopier.copyToNew(ub, UserBaseResult.class);
        }
    }

}
