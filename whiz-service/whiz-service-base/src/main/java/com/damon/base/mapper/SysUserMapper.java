package com.damon.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.damon.base.entity.SysUserPage;
import com.damon.common.entity.SysUser;
import com.damon.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户表 Mapper 接口
 *
 */
public interface SysUserMapper extends SuperMapper<SysUser> {
    /**
     * 分页查询用户列表
     * @param params
     * @return
     */
    List<SysUser> findList(@Param("u") SysUserPage params);
}
