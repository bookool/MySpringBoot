package com.bookool.myboot.common.utils;

import net.sf.cglib.beans.BeanCopier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给BeanCopier包装缓存，以提高效率
 *
 * @author Tommy
 */
public class CachedBeanCopier {

    private static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap<>();

    /**
     * 在缓存中提取BeanCopier进行拷贝一个新对象返回
     *
     * @param sourceObj   源对象
     * @param targetClazz 目标类型
     */
    @Nullable
    public static <T> T copyToNew(@NotNull Object sourceObj, Class<T> targetClazz) {
        T targetObj;
        try {
            targetObj = targetClazz.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        copy(sourceObj, targetObj);
        return targetObj;
    }

    /**
     * 在缓存中提取BeanCopier进行拷贝一个新List返回
     *
     * @param sourceList  源List对象
     * @param targetClazz 目标类型
     */
    @Nullable
    public static <P, T> List<T> copyList(@NotNull List<P> sourceList, Class<T> targetClazz) {
        List<T> targetList = new ArrayList<>();
        for (P sobj : sourceList) {
            T tobj;
            try {
                tobj = targetClazz.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
            copy(sobj, tobj);
            targetList.add(tobj);
        }
        return targetList;
    }

    /**
     * 在缓存中提取BeanCopier进行对象拷贝
     *
     * @param sourceObj 源对象
     * @param targetObj 目标对象
     */
    public static void copy(@NotNull Object sourceObj, @NotNull Object targetObj) {
        BeanCopier copier = getCopier(sourceObj.getClass(), targetObj.getClass());
        copier.copy(sourceObj, targetObj, null);
    }

    /**
     * 在缓存中提取BeanCopier
     *
     * @param sourceClazz 源对象类型
     * @param targetClazz 目标对象类型
     * @return BeanCopier
     */
    private static BeanCopier getCopier(@NotNull Class<?> sourceClazz, @NotNull Class<?> targetClazz) {
        String key = sourceClazz.getName() + targetClazz.getName();
        BeanCopier copier = null;
        // 如果缓存的BeanCopier不存在，则创建
        if (!BEAN_COPIERS.containsKey(key)) {
            // 创建一个BeanCopier缓存，需要考虑并发问题
            synchronized (BEAN_COPIERS) {
                // 在锁机制内再次判断，防止并发时重复创建（其实重复创建不会有问题，以此方式提高效率）
                if (!BEAN_COPIERS.containsKey(key)) {
                    copier = BeanCopier.create(sourceClazz, targetClazz, false);
                    BEAN_COPIERS.put(key, copier);
                }
            }
        }
        if (copier == null) {
            copier = BEAN_COPIERS.get(key);
        }
        return copier;
    }

}