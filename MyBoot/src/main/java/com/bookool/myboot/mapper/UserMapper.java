package com.bookool.myboot.mapper;

import com.bookool.myboot.domain.dto.param.UserParam;
import com.bookool.myboot.domain.dto.result.UserResult;
import com.bookool.myboot.domain.entity.User;
import com.bookool.myboot.common.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * user 用户基础信息表 Mapper
 *
 * @author Tommy
 * @date 2019-05-25 18:36:33
 */
@Mapper
public interface UserMapper extends BaseMapper<User, UserParam> {

    // #####################以下为自动生成的基础模板代码############################################################

    /**
     * 基础模板 取得符合条件的 user 的记录个数
     *
     * @param userParam 查询条件对象
     * @return 列表数量
     */
    Long getListCount(UserParam userParam);

    /**
     * 基础模板 取得符合条件的 user 列表手动分页
     *
     * @param userParam 查询条件对象
     * @return User列表
     */
    List<UserResult> getListPage(UserParam userParam);

    /**
     * 基础模板 取得符合条件的 user 列表
     *
     * @param userParam 查询条件对象
     * @return User列表
     */
    List<UserResult> getList(UserParam userParam);

    // ^^^^^^^^^^^^^^^^^^^^^以上为自动生成的基础模板代码^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

}
