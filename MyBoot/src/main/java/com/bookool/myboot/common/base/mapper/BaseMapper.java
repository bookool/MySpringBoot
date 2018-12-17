package com.bookool.myboot.common.base.mapper;

import com.bookool.myboot.common.base.SnowflakeIdHandler;
import com.bookool.myboot.common.utils.CachedBeanCopier;
import com.bookool.myboot.domain.BaseEntity;
import com.bookool.myboot.domain.dto.BaseParam;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.Date;

/**
 * 所有 Mapper 必须继承此接口，此接口只实现了4个默认方法
 * 根据阿里开发规范，所有数据表必须实现 id、gmtCreate、gmtModified 三个字段
 * 这里实现在插入和更新的时候，自动对这三个字段赋值
 * 本项目所有主键都使用 snowflake 方法生成，不使用数据库自增以及uuid
 * 其中插入分为两个方法，分别为自动生成主键和不自动生成主键（某些扩展表和主表使用相同主键）
 * 其中更新也分为两个方法，分别为通过主键更新以及通过 Weekend 传入条件参数
 * 本类中方法均使用 tk.mapper 的方法实现
 * 实际开发中遇到插入和更新，必须使用此类中的方法，不允许使用其他方法
 *
 * @param <T> 实体类型
 * @param <P> dto传参
 *
 * @author Tommy
 */
public interface BaseMapper<T extends BaseEntity, P extends BaseParam> extends Mapper<T> {

    /**
     * 自动生成id、gmtCreate、gmtModified，调用mapper insertSelective添加记录
     *
     * @param param       要添加的记录
     * @param entityClazz 实体类型
     * @return 数据库执行完毕影响的行数
     */
    default int insertSelectiveGenerate(P param, Class<T> entityClazz) {
        if (param.getId() != null || param.getGmtCreate() != null || param.getGmtModified() != null) {
            throw new RuntimeException("id、gmtCreate、gmtModified must be null.");
        }
        Long id = SnowflakeIdHandler.nextId();
        Date datenow = new Date();
        param.setId(id);
        param.setGmtCreate(datenow);
        param.setGmtModified(datenow);
        T para = CachedBeanCopier.copyToNew(param, entityClazz);
        return insertSelective(para);
    }

    /**
     * 添加一条带主键id值的记录，自动生成gmtCreate、gmtModified
     * 调用mapper insertSelective添加记录
     * 使用场景：对于某类信息的扩展表，主键id和主表的id保持一致，则不用生成id，直接拿主表id值来添加
     *
     * @param param       要添加的记录
     * @param entityClazz 实体类型
     * @return 数据库执行完毕影响的行数
     */
    default int insertWithIdSelectiveGenerate(P param, Class<T> entityClazz) {
        if (param.getId() == null || param.getGmtCreate() != null || param.getGmtModified() != null) {
            throw new RuntimeException("id not be null. gmtCreate、gmtModified must be null.");
        }
        Date datenow = new Date();
        param.setGmtCreate(datenow);
        param.setGmtModified(datenow);
        T para = CachedBeanCopier.copyToNew(param, entityClazz);
        return insertSelective(para);
    }

    /**
     * 自动生成gmtModified，调用mapper updateByExampleSelective更新记录
     *
     * @param param       要更新的记录
     * @param entityClazz 实体类型
     * @param weekend     条件参数
     * @return 数据库执行完毕影响的行数
     */
    default int updateByWeekendSelectiveGenerate(P param, Class<T> entityClazz, Weekend<T> weekend) {
        if (param.getId() != null || param.getGmtCreate() != null || param.getGmtModified() != null) {
            throw new RuntimeException("id、gmtCreate、gmtModified must be null.");
        }
        Date datenow = new Date();
        param.setGmtModified(datenow);
        T para = CachedBeanCopier.copyToNew(param, entityClazz);
        return updateByExampleSelective(para, weekend);
    }

    /**
     * 自动生成gmtModified，调用mapper updateByPrimaryKeySelective根据主键更新记录
     *
     * @param param       要更新的记录
     * @param entityClazz 实体类型
     * @return 数据库执行完毕影响的行数
     */
    default int updateByPrimaryKeySelectiveGenerate(P param, Class<T> entityClazz) {
        if (param.getId() == null || param.getGmtCreate() != null || param.getGmtModified() != null) {
            throw new RuntimeException("id not be null. gmtCreate、gmtModified must be null.");
        }
        Date datenow = new Date();
        param.setGmtModified(datenow);
        T para = CachedBeanCopier.copyToNew(param, entityClazz);
        return updateByPrimaryKeySelective(para);
    }

}
