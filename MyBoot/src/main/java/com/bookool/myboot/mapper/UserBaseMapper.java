package com.bookool.myboot.mapper;

import com.bookool.myboot.domain.dto.param.UserBaseParam;
import com.bookool.myboot.domain.dto.result.UserBaseResult;
import com.bookool.myboot.domain.entity.UserBase;
import com.bookool.myboot.common.base.mapper.BaseMapper;
import com.bookool.myboot.service.impl.base.PageListParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * UserBaseMapper 用户基础信息表 Mapper
 *
 * @author Tommy
 */
@Mapper
public interface UserBaseMapper extends BaseMapper<UserBase, UserBaseParam> {

    // #####################以下为自动生成的基础模板代码############################################################
    //region >>>自动生成的基础模板代码<<<

    /**
     * 基础模板 取得符合条件的 user_base 的记录个数
     *
     * @param param 查询条件对象
     * @return 列表数量
     */
    Long getListCount(UserBaseParam param);

    /**
     * 基础模板 取得符合条件的 user_base 列表手动分页
     *
     * @param param 查询条件对象
     * @return user_base 列表
     */
    List<UserBaseResult> getListPage(PageListParam param);

    /**
     * 基础模板 取得符合条件的 user_base 列表
     *
     * @param param 查询条件对象
     * @return user_base 列表
     */
    List<UserBaseResult> getList(PageListParam param);

    //endregion
    // ^^^^^^^^^^^^^^^^^^^^^以上为自动生成的基础模板代码^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

}
