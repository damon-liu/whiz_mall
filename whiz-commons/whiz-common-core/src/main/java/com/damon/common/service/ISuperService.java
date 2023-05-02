package com.damon.common.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.damon.common.component.lock.DistributedLock;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: damon.liu
 * @createDate: 2020/10/16
 */
public interface ISuperService<T> extends IService<T> {

    /**
     * 幂等性新增记录
     * 例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveIdempotency(sysUser, lock
     * , LOCK_KEY_USERNAME+username
     * , new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return boolean
     * throws Exception
     */
    boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception;

    boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) throws Exception;

    /**
     * 幂等性新增或更新记录
     * 例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveOrUpdateIdempotency(sysUser, lock
     * , LOCK_KEY_USERNAME+username
     * , new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return boolean
     * throws Exception
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception;

    boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) throws Exception;

    /**
     * 查询出一个 根据code查询
     *
     * @param key 列名
     * @param val 列值
     * @return 结果
     */
    default T getByKey(String key, Object val) {
        return this.getOne(new QueryWrapper<T>().eq(key, val).last("limit 1"));
    }

    /**
     * 根据编号 查下集合
     *
     * @param key 列名
     * @param val 列值
     * @return 结果集
     */
    default List<T> getListByKey(String key, Object val) {
        return this.list(new QueryWrapper<T>().eq(key, val));
    }


    /**
     * 统计次数
     *
     * @param key 列名
     * @param val 列值
     * @return 结果
     */
    default Integer count(String key, Object val) {
        return ((Long) this.count(new QueryWrapper<T>().eq(key, val))).intValue();
    }

    /**
     * 根据某一个key删除
     *
     * @param key 列名
     * @param val 列值
     * @return 结果
     */
    default boolean deleteByKey(String key, Object val) {
        return this.remove(new QueryWrapper<T>().eq(key, val));
    }

    /**
     * 查询出一个 根据code查询
     *
     * @param key  列名
     * @param list in 集合
     * @return 结果
     */
    default List<T> listIn(String key, Collection<?> list) {
        return this.list(new QueryWrapper<T>().in(key, list));
    }

    /**
     * 唯一性验证，编辑时用
     *
     * @param columnName  列名
     * @param columnValue 列值
     * @param pkName      主键列名
     * @param pkValue     主键值
     * @return
     */
    default boolean isExists(String columnName, Object columnValue, String pkName, Object pkValue) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>().eq(columnName, columnValue);
        if (StrUtil.isNotBlank(pkName) && pkValue != null) {
            queryWrapper.ne(pkName, pkValue);
        }
        return CollUtil.isNotEmpty(this.list(queryWrapper));
    }

    /**
     * 唯一性验证，编辑时用(待状态判断)
     *
     * @param columnName      列名
     * @param columnValue     列值
     * @param stateColumnName 状态列名
     * @param columnValue     状态列值
     * @param pkName          主键列名
     * @param pkValue         主键值
     * @return
     */
    default boolean isStateExists(String columnName, Object columnValue, String stateColumnName, Object stateColumnValue,
                                  String pkName, Object pkValue) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>().eq(columnName, columnValue).eq(stateColumnName, stateColumnValue);
        if (StrUtil.isNotBlank(pkName) && pkValue != null) {
            queryWrapper.ne(pkName, pkValue);
        }
        return CollUtil.isNotEmpty(this.list(queryWrapper));
    }


}
