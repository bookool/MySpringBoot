package com.bookool.myboot.common.base.mapper;

import com.bookool.myboot.common.base.SnowflakeIdHandler;
import com.bookool.myboot.common.utils.CachedBeanCopier;
import com.bookool.myboot.domain.BaseEntity;
import com.bookool.myboot.domain.dto.BaseParam;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.Date;

/**
 * Mapper 基类
 *
 * @param <P> dto传参
 * @param <T> 实体类型
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
