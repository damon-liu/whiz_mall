package com.damon.base.service;


import com.damon.common.entity.PageResult;
import com.damon.common.entity.Result;
import com.damon.common.entity.SysRole;
import com.damon.common.service.ISuperService;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 7:50
 */
public interface ISysRoleService extends ISuperService<SysRole> {

    void saveRole(SysRole sysRole) throws Exception;

    void deleteRole(Long id);

    /**
     * 角色列表
     * @param params
     * @return
     */
    PageResult<SysRole> findRoles(Map<String, Object> params);

    /**
     * 新增或更新角色
     * @param sysRole
     * @return Result
     */
    Result saveOrUpdateRole(SysRole sysRole) throws Exception;

    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> findAll();
}
